package com.dwarfeng.logicengine.sdk.handler.guarder;

import com.dwarfeng.logicengine.stack.exception.GuarderException;
import com.dwarfeng.logicengine.stack.handler.Guarder;

/**
 * 守卫器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractGuarder implements Guarder {

    @Override
    public Executor newExecutor() throws GuarderException {
        try {
            return doNewExecutor();
        } catch (GuarderException e) {
            throw e;
        } catch (Exception e) {
            throw new GuarderException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractGuarder{}";
    }
}
