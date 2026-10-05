package com.dwarfeng.logicengine.impl.handler.performer.cmd;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 命令行执行器。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component("cmdPerformerRegistry.cmdPerformer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CmdPerformer extends AbstractPerformer {

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;

    private final CmdPerformerConfig config;

    public CmdPerformer(ApplicationContext ctx, ThreadPoolTaskExecutor executor, CmdPerformerConfig config) {
        this.ctx = ctx;
        this.executor = executor;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(CmdExecutor.class, ctx, executor, config);
    }

    @Override
    public String toString() {
        return "CmdPerformer{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", config=" + config +
                '}';
    }
}
