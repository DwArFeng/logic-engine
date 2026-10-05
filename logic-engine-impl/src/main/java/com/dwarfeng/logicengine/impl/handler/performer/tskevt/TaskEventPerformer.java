package com.dwarfeng.logicengine.impl.handler.performer.tskevt;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 任务事件执行器。
 *
 * <p>
 * 该执行器持有任务事件消息配置，并在每次 {@link #newExecutor()} 调用时创建一个与该配置绑定的任务事件执行器执行器。
 * 执行器以当前任务为主键创建任务事件，并将配置中的固定文本写入任务事件消息，不读取任务变量，也不修改任务模态。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component("taskEventPerformerRegistry.taskEventPerformer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskEventPerformer extends AbstractPerformer {

    private final ApplicationContext ctx;

    private final TaskEventPerformerConfig config;

    public TaskEventPerformer(ApplicationContext ctx, TaskEventPerformerConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(TaskEventExecutor.class, config);
    }

    @Override
    public String toString() {
        return "TaskEventPerformer{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
