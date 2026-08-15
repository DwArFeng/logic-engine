package com.dwarfeng.logicengine.impl.handler.dispatcher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.handler.dispatcher.AbstractDispatcher;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Kafka 调度器。
 *
 * <p>
 * 基于 Kafka 实现的调度器，利用 Kafka 消费组机制实现多个接收节点之间的负载均衡。
 * 该调度器支持默认、轮询和随机三种分区选择策略，并周期性刷新主题分区信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class KafkaDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "kafka";

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDispatcher.class);

    private final KafkaSender kafkaSender;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public KafkaDispatcher(KafkaSender kafkaSender) {
        super(DISPATCHER_TYPE);
        this.kafkaSender = kafkaSender;
    }

    @Override
    protected void doStart() {
        LOGGER.info("Kafka 调度器上线...");
        kafkaSender.start();
    }

    @Override
    protected void doStop() {
        LOGGER.info("Kafka 调度器下线...");
        kafkaSender.stop();
    }

    @Override
    protected void doDispatch(LongIdKey sectionKey) {
        kafkaSender.send(JSON.toJSONString(FastJsonLongIdKey.of(sectionKey)));
    }

    @Override
    public String toString() {
        return "KafkaDispatcher{" +
                "kafkaSender=" + kafkaSender +
                '}';
    }

    /**
     * Kafka 发送器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component("kafkaDispatcher.kafkaSender")
    public static class KafkaSender {

        private static final String LOAD_BALANCE_MODE_DEFAULT = "DEFAULT";
        private static final String LOAD_BALANCE_MODE_ROUND_ROBIN = "ROUND_ROBIN";
        private static final String LOAD_BALANCE_MODE_RANDOM = "RANDOM";

        private final KafkaTemplate<String, String> kafkaTemplate;
        private final ThreadPoolTaskScheduler scheduler;

        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.load_balance_mode}")
        private String loadBalanceMode;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.partition_check_interval}")
        private long partitionCheckInterval;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.topic.dispatch}")
        private String dispatchTopic;

        private final Lock lock = new ReentrantLock();

        private ScheduledFuture<?> partitionCheckFuture;
        private List<PartitionInfo> partitionInfos;
        private int roundRobinIndex;

        public KafkaSender(
                @Qualifier("kafkaDispatcher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
                @Qualifier("scheduler") ThreadPoolTaskScheduler scheduler
        ) {
            this.kafkaTemplate = kafkaTemplate;
            this.scheduler = scheduler;
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void start() {
            lock.lock();
            try {
                refreshPartitions();
                partitionCheckFuture = scheduler.scheduleAtFixedRate(this::refreshPartitions, partitionCheckInterval);
            } finally {
                lock.unlock();
            }
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void stop() {
            lock.lock();
            try {
                if (Objects.nonNull(partitionCheckFuture)) {
                    partitionCheckFuture.cancel(true);
                    partitionCheckFuture = null;
                }
                partitionInfos = null;
                roundRobinIndex = 0;
            } finally {
                lock.unlock();
            }
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void send(String message) {
            lock.lock();
            try {
                switch (StringUtils.upperCase(loadBalanceMode)) {
                    case LOAD_BALANCE_MODE_DEFAULT:
                        kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, message));
                        break;
                    case LOAD_BALANCE_MODE_ROUND_ROBIN:
                        sendRoundRobin(message);
                        break;
                    case LOAD_BALANCE_MODE_RANDOM:
                        sendRandom(message);
                        break;
                    default:
                        throw new IllegalStateException("未知的负载均衡模式: " + loadBalanceMode);
                }
            } finally {
                lock.unlock();
            }
        }

        private void sendRoundRobin(String message) {
            List<PartitionInfo> partitions = requirePartitions();
            int partition = partitions.get(roundRobinIndex).partition();
            kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, partition, null, message));
            roundRobinIndex = (roundRobinIndex + 1) % partitions.size();
        }

        private void sendRandom(String message) {
            List<PartitionInfo> partitions = requirePartitions();
            int partition = partitions.get((int) (Math.random() * partitions.size())).partition();
            kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, partition, null, message));
        }

        private List<PartitionInfo> requirePartitions() {
            if (Objects.isNull(partitionInfos) || partitionInfos.isEmpty()) {
                throw new IllegalStateException("Kafka 调度主题不存在可用分区: " + dispatchTopic);
            }
            return partitionInfos;
        }

        private void refreshPartitions() {
            lock.lock();
            try {
                List<PartitionInfo> refreshedPartitions = kafkaTemplate.partitionsFor(dispatchTopic);
                if (refreshedPartitions.isEmpty()) {
                    throw new IllegalStateException("Kafka 调度主题不存在可用分区: " + dispatchTopic);
                }
                partitionInfos = new ArrayList<>(refreshedPartitions);
                roundRobinIndex %= partitionInfos.size();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return "KafkaSender{" +
                    "dispatchTopic='" + dispatchTopic + '\'' +
                    ", loadBalanceMode='" + loadBalanceMode + '\'' +
                    '}';
        }
    }

    /**
     * Kafka 生产者配置。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Configuration("kafkaDispatcher.kafkaConfiguration")
    @EnableKafka
    public static class KafkaConfiguration {

        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.retries}")
        private int retries;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.linger}")
        private long linger;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.buffer_memory}")
        private long bufferMemory;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.batch_size}")
        private int batchSize;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.acks}")
        private String acks;
        @Value("${com.dwarfeng.logicengine.dispatcher.kafka.transaction_prefix}")
        private String transactionPrefix;

        @SuppressWarnings("DuplicatedCode")
        @Bean("kafkaDispatcher.producerProperties")
        public Map<String, Object> producerProperties() {
            Map<String, Object> properties = new HashMap<>();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            properties.put(ProducerConfig.RETRIES_CONFIG, retries);
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            properties.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            properties.put(ProducerConfig.ACKS_CONFIG, acks);
            return properties;
        }

        @Bean("kafkaDispatcher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            DefaultKafkaProducerFactory<String, String> factory =
                    new DefaultKafkaProducerFactory<>(producerProperties());
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            return factory;
        }

        @Bean("kafkaDispatcher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory(), true);
        }

        @Bean("kafkaDispatcher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            return new KafkaTransactionManager<>(producerFactory());
        }
    }
}
