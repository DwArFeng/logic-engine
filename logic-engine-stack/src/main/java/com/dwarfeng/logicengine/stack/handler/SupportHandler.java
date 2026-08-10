package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置执行器。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.0.0
     */
    void resetPerformer() throws HandlerException;
}
