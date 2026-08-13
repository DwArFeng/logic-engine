package com.dwarfeng.logicengine.impl.handler.guarder.always;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 无条件守卫器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("alwaysGuarderRegistry.alwaysGuarder")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AlwaysGuarder extends AbstractGuarder {

    private final ApplicationContext ctx;

    public AlwaysGuarder(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(AlwaysExecutor.class);
    }

    @Override
    public String toString() {
        return "AlwaysGuarder{" +
                "ctx=" + ctx +
                '}';
    }
}
