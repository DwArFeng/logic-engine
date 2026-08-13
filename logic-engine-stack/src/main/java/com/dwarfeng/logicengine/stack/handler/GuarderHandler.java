package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 守卫器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderHandler extends Handler {

    /**
     * 根据指定的守卫器信息构造守卫器。
     *
     * @param type  守卫器类型。
     * @param param 守卫器参数。
     * @return 构造的守卫器。
     * @throws HandlerException 处理器异常。
     */
    Guarder make(String type, String param) throws HandlerException;
}
