package com.dwarfeng.logicengine.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.bean.dto.FastJsonPurgeFinishedResult;
import com.dwarfeng.logicengine.sdk.bean.entity.FastJsonSection;
import com.dwarfeng.logicengine.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本地 Kafka 推送器。
 *
 * <p>
 * 该推送器使用独立的 Kafka 生产者和事务管理器发送事件，避免与项目中的其它 Kafka 组件共享事务资源。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@Import({NativeKafkaPusher.KafkaSender.class, NativeKafkaPusherConfiguration.class})
public class NativeKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "kafka.native";

    private final KafkaSender kafkaSender;

    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.task_finished}")
    private String taskFinishedTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.task_failed}")
    private String taskFailedTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.task_expired}")
    private String taskExpiredTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.task_died}")
    private String taskDiedTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.supervise_reset}")
    private String superviseResetTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.job_reset}")
    private String jobResetTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.purge_finished}")
    private String purgeFinishedTopic;
    @Value("${com.dwarfeng.logicengine.pusher.kafka.native.topic.purge_failed}")
    private String purgeFailedTopic;

    public NativeKafkaPusher(KafkaSender kafkaSender) {
        super(PUSHER_TYPE);
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void taskFinished(Section section) {
        kafkaSender.send(taskFinishedTopic, JSON.toJSONString(FastJsonSection.of(section)));
    }

    @Override
    public void taskFailed(Section section) {
        kafkaSender.send(taskFailedTopic, JSON.toJSONString(FastJsonSection.of(section)));
    }

    @Override
    public void taskExpired(Section section) {
        kafkaSender.send(taskExpiredTopic, JSON.toJSONString(FastJsonSection.of(section)));
    }

    @Override
    public void taskDied(Section section) {
        kafkaSender.send(taskDiedTopic, JSON.toJSONString(FastJsonSection.of(section)));
    }

    @Override
    public void superviseReset() {
        kafkaSender.send(superviseResetTopic, StringUtils.EMPTY);
    }

    @Override
    public void jobReset() {
        kafkaSender.send(jobResetTopic, StringUtils.EMPTY);
    }

    @Override
    public void purgeFinished(PurgeFinishedResult result) {
        kafkaSender.send(purgeFinishedTopic, JSON.toJSONString(FastJsonPurgeFinishedResult.of(result)));
    }

    @Override
    public void purgeFailed() {
        kafkaSender.send(purgeFailedTopic, StringUtils.EMPTY);
    }

    @Override
    public String toString() {
        return "NativeKafkaPusher{" +
                "kafkaSender=" + kafkaSender +
                ", taskFinishedTopic='" + taskFinishedTopic + '\'' +
                ", taskFailedTopic='" + taskFailedTopic + '\'' +
                ", taskExpiredTopic='" + taskExpiredTopic + '\'' +
                ", taskDiedTopic='" + taskDiedTopic + '\'' +
                ", superviseResetTopic='" + superviseResetTopic + '\'' +
                ", jobResetTopic='" + jobResetTopic + '\'' +
                ", purgeFinishedTopic='" + purgeFinishedTopic + '\'' +
                ", purgeFailedTopic='" + purgeFailedTopic + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }

    /**
     * Kafka 发送器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component("nativeKafkaPusher.kafkaSender")
    public static class KafkaSender {

        private final KafkaTemplate<String, String> kafkaTemplate;

        public KafkaSender(
                @Qualifier("nativeKafkaPusher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate
        ) {
            this.kafkaTemplate = kafkaTemplate;
        }

        @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
        public void send(String topic, String message) {
            kafkaTemplate.send(topic, message);
        }

        @Override
        public String toString() {
            return "KafkaSender{" +
                    "kafkaTemplate=" + kafkaTemplate +
                    '}';
        }
    }

}
