package com.dwarfeng.logicengine.sdk.handler.performer;

import com.dwarfeng.logicengine.stack.handler.Performer.Context;
import com.dwarfeng.logicengine.stack.handler.Performer.Executor;

/**
 * 执行器执行器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractExecutor implements Executor {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractExecutor{" +
                "context=" + context +
                '}';
    }
}
