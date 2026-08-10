package com.dwarfeng.logicengine.sdk.handler;

import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.handler.Performer;

/**
 * 执行器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PerformerMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的执行器信息生成一个执行器对象。
     *
     * <p>
     * 可以保证传入的执行器信息中的类型是支持的。
     *
     * @param type  执行器类型。
     * @param param 执行器参数。
     * @return 构造的执行器。
     * @throws PerformerException 执行器异常。
     */
    Performer makePerformer(String type, String param) throws PerformerException;
}
