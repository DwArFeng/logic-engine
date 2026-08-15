package com.dwarfeng.logicengine.impl.handler.pusher;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地 Kafka 推送器配置。
 *
 * <p>
 * 该配置为本地 Kafka 推送器提供独立的生产者工厂、Kafka 模板以及事务管理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Configuration("nativeKafkaPusher.kafkaConfiguration")
@EnableKafka
public class NativeKafkaPusherConfiguration {

    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.bootstrap_servers}")
    private String producerBootstrapServers;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.retries}")
    private int retries;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.linger}")
    private long linger;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.buffer_memory}")
    private long bufferMemory;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.batch_size}")
    private int batchSize;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.acks}")
    private String acks;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.transaction_prefix}")
    private String transactionPrefix;

    @Bean("nativeKafkaPusher.producerProperties")
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

    @Bean("nativeKafkaPusher.producerFactory")
    public ProducerFactory<String, String> producerFactory() {
        DefaultKafkaProducerFactory<String, String> factory =
                new DefaultKafkaProducerFactory<>(producerProperties());
        factory.setTransactionIdPrefix(transactionPrefix);
        factory.setKeySerializer(new StringSerializer());
        factory.setValueSerializer(new StringSerializer());
        return factory;
    }

    @Bean("nativeKafkaPusher.kafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(), true);
    }

    @Bean("nativeKafkaPusher.kafkaTransactionManager")
    public KafkaTransactionManager<String, String> kafkaTransactionManager() {
        return new KafkaTransactionManager<>(producerFactory());
    }
}
