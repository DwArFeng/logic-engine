package com.dwarfeng.logicengine.impl.handler.guarder.groovy;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractExecutor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 守卫器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("groovyGuarderRegistry.groovyExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyExecutor extends AbstractExecutor {

    private final Processor processor;

    public GroovyExecutor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public boolean test() throws Exception {
        return processor.test(context);
    }

    @Override
    public String toString() {
        return "GroovyExecutor{" +
                "processor=" + processor +
                ", context=" + context +
                '}';
    }
}
