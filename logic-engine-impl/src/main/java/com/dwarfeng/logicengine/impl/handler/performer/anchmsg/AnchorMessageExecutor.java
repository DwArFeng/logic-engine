package com.dwarfeng.logicengine.impl.handler.performer.anchmsg;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractExecutor;
import com.dwarfeng.logicengine.stack.bean.dto.TaskUpdateModalInfo;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 锚点消息执行器执行器。
 *
 * <p>
 * 该执行器把配置中的固定文本写入上下文对应任务的锚点消息，用于展示任务当前阶段的提示信息。
 * 消息内容完全来自配置，不依赖任务变量、状态转移的锚点状态或目标状态。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component("anchorMessagePerformerRegistry.anchorMessageExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnchorMessageExecutor extends AbstractExecutor {

    private final AnchorMessagePerformerConfig config;

    public AnchorMessageExecutor(AnchorMessagePerformerConfig config) {
        this.config = config;
    }

    @Override
    public void execute() throws Exception {
        // 将配置中的锚点消息写入当前任务。
        context.updateTaskModal(new TaskUpdateModalInfo(context.getTask().getKey(), config.getAnchorMessage()));
    }

    @Override
    public String toString() {
        return "AnchorMessageExecutor{" +
                "config=" + config +
                ", context=" + context +
                '}';
    }
}
