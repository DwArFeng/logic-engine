package com.dwarfeng.logicengine.impl.handler.performer.tskevt;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformerRegistry;
import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.exception.PerformerMakeException;
import com.dwarfeng.logicengine.stack.handler.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 任务事件执行器注册。
 *
 * <p>
 * 该注册实现将参数解析为 {@link TaskEventPerformerConfig}，对配置进行静态校验，并为每次构造请求创建独立的
 * 任务事件执行器实例。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component
public class TaskEventPerformerRegistry extends AbstractPerformerRegistry {

    public static final String PERFORMER_TYPE = "task_event_performer";

    private final ApplicationContext ctx;

    public TaskEventPerformerRegistry(ApplicationContext ctx) {
        super(PERFORMER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "任务事件执行器";
    }

    @Override
    public String provideDescription() {
        return "将配置中的任务事件消息写入任务。";
    }

    @Override
    public String provideExampleParam() {
        TaskEventPerformerConfig config = new TaskEventPerformerConfig("任务已开始执行。");
        return JSON.toJSONString(config, true);
    }

    @Override
    public Performer makePerformer(String type, String param) throws PerformerException {
        try {
            if (Objects.isNull(param) || param.trim().isEmpty()) {
                throw new IllegalArgumentException("任务事件执行器配置不能为空");
            }
            TaskEventPerformerConfig config = JSON.parseObject(param, TaskEventPerformerConfig.class);
            if (Objects.isNull(config)) {
                throw new IllegalArgumentException("任务事件执行器配置不能为空");
            }
            if (Objects.isNull(config.getMessage()) || config.getMessage().trim().isEmpty()) {
                throw new IllegalArgumentException("任务事件执行器配置项 message 不能为空");
            }
            return ctx.getBean(TaskEventPerformer.class, ctx, config);
        } catch (Exception e) {
            throw new PerformerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "TaskEventPerformerRegistry{" +
                "ctx=" + ctx +
                ", performerType='" + performerType + '\'' +
                '}';
    }
}
