package com.dwarfeng.logicengine.impl.handler.performer.tskevt;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractExecutor;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 任务事件执行器执行器。
 *
 * <p>
 * 该执行器把配置中的固定文本作为事件消息，写入上下文对应任务的任务事件。
 * 任务主键取自执行器上下文中的当前任务，事件的发生时间由任务事件操作处理器在创建时补齐，消息内容完全来自配置，
 * 不依赖任务变量、状态转移的锚点状态或目标状态。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component("taskEventPerformerRegistry.taskEventExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskEventExecutor extends AbstractExecutor {

    private final TaskEventPerformerConfig config;

    public TaskEventExecutor(TaskEventPerformerConfig config) {
        this.config = config;
    }

    @Override
    public void execute() throws Exception {
        // 将配置中的任务事件消息写入当前任务。
        context.createTaskEvent(new TaskEventCreateInfo(context.getTask().getKey(), null, config.getMessage()));
    }

    @Override
    public String toString() {
        return "TaskEventExecutor{" +
                "config=" + config +
                ", context=" + context +
                '}';
    }
}
