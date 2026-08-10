package com.dwarfeng.logicengine.impl.handler.performer.groovy;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("groovyPerformerRegistry.groovyPerformer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyPerformer extends AbstractPerformer {

    private final ApplicationContext ctx;

    private final Processor processor;

    public GroovyPerformer(ApplicationContext ctx, Processor processor) {
        this.ctx = ctx;
        this.processor = processor;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(GroovyExecutor.class, processor);
    }

    @Override
    public String toString() {
        return "GroovyPerformer{" +
                "ctx=" + ctx +
                ", processor=" + processor +
                '}';
    }
}
