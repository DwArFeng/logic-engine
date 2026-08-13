package com.dwarfeng.logicengine.impl.handler.guarder.groovy;

import com.dwarfeng.logicengine.stack.handler.Guarder;

/**
 * Groovy 处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Processor {

    /**
     * 判断状态转移条件是否成立。
     *
     * @param context 守卫器上下文。
     * @return 条件是否成立。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    boolean test(Guarder.Context context) throws Exception;
}
