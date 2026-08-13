package com.dwarfeng.logicengine.sdk.handler;

import com.dwarfeng.logicengine.stack.exception.GuarderException;
import com.dwarfeng.logicengine.stack.handler.Guarder;

/**
 * 守卫器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderMaker {

    /**
     * 返回构造器是否支持指定的守卫器类型。
     *
     * @param type 守卫器类型。
     * @return 构造器是否支持指定的守卫器类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的守卫器信息构造守卫器。
     *
     * <p>
     * 可以保证传入的守卫器类型是该构造器支持的类型。
     *
     * @param type  守卫器类型。
     * @param param 守卫器参数。
     * @return 构造的守卫器。
     * @throws GuarderException 守卫器异常。
     */
    Guarder makeGuarder(String type, String param) throws GuarderException;
}
