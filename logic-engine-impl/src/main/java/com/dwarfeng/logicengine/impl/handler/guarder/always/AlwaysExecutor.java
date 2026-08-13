package com.dwarfeng.logicengine.impl.handler.guarder.always;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractExecutor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 无条件守卫器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("alwaysGuarderRegistry.alwaysExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AlwaysExecutor extends AbstractExecutor {

    @Override
    public boolean test() {
        return true;
    }
}
