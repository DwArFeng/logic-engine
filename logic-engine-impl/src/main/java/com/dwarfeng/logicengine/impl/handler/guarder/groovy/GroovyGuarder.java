package com.dwarfeng.logicengine.impl.handler.guarder.groovy;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 守卫器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("groovyGuarderRegistry.groovyGuarder")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyGuarder extends AbstractGuarder {

    private final ApplicationContext ctx;

    private final Processor processor;

    public GroovyGuarder(ApplicationContext ctx, Processor processor) {
        this.ctx = ctx;
        this.processor = processor;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(GroovyExecutor.class, processor);
    }

    @Override
    public String toString() {
        return "GroovyGuarder{" +
                "ctx=" + ctx +
                ", processor=" + processor +
                '}';
    }
}
