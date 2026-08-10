package com.dwarfeng.logicengine.sdk.handler.performer;

import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.handler.Performer;

/**
 * 执行器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractPerformer implements Performer {

    @Override
    public Executor newExecutor() throws PerformerException {
        try {
            return doNewExecutor();
        } catch (PerformerException e) {
            throw e;
        } catch (Exception e) {
            throw new PerformerException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractPerformer{}";
    }
}
