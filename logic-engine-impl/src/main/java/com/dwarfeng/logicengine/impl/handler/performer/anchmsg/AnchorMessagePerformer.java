package com.dwarfeng.logicengine.impl.handler.performer.anchmsg;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 锚点消息执行器。
 *
 * <p>
 * 该执行器持有锚点消息配置，并在每次 {@link #newExecutor()} 调用时创建一个与该配置绑定的锚点消息执行器执行器。
 * 执行器将配置中的固定文本写入任务锚点消息，不读取任务变量，也不创建任务事件。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component("anchorMessagePerformerRegistry.anchorMessagePerformer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnchorMessagePerformer extends AbstractPerformer {

    private final ApplicationContext ctx;

    private final AnchorMessagePerformerConfig config;

    public AnchorMessagePerformer(ApplicationContext ctx, AnchorMessagePerformerConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(AnchorMessageExecutor.class, config);
    }

    @Override
    public String toString() {
        return "AnchorMessagePerformer{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
