package com.dwarfeng.logicengine.impl.handler.performer.groovy;

import com.dwarfeng.logicengine.sdk.handler.performer.AbstractExecutor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 执行器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("groovyPerformerRegistry.groovyExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyExecutor extends AbstractExecutor {

    private final Processor processor;

    public GroovyExecutor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void execute() throws Exception {
        processor.execute(context);
    }

    @Override
    public String toString() {
        return "GroovyExecutor{" +
                "processor=" + processor +
                ", context=" + context +
                '}';
    }
}
