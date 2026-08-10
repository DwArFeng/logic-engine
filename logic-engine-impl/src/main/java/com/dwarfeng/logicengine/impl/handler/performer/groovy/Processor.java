package com.dwarfeng.logicengine.impl.handler.performer.groovy;

import com.dwarfeng.logicengine.stack.handler.Performer;

/**
 * Groovy 处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Processor {

    /**
     * 执行状态转移动作。
     *
     * <p>
     * 该方法被调用时，需要按照预定的逻辑执行状态转移动作，并通过 {@link Performer.Context} 获取任务及状态信息，
     * 或对任务变量进行维护。
     *
     * @param context 执行器上下文。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    void execute(Performer.Context context) throws Exception;
}
