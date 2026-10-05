package com.dwarfeng.logicengine.impl.handler.guarder.compare;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 比较守卫器。
 *
 * <p>
 * 该守卫器持有比较配置，并在每次 {@link #newExecutor()} 调用时创建一个与该配置绑定的比较执行器。
 * 执行器按照配置解析左右操作数，比较域由左操作数的类型确定，比较结果决定状态转移是否发生。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component("compareGuarderRegistry.compareGuarder")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompareGuarder extends AbstractGuarder {

    private final ApplicationContext ctx;

    private final CompareGuarderConfig config;

    public CompareGuarder(ApplicationContext ctx, CompareGuarderConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(CompareExecutor.class, config);
    }

    @Override
    public String toString() {
        return "CompareGuarder{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
