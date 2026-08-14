package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置主管功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetSupervise() throws HandlerException;

    /**
     * 重置作业功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetJob() throws HandlerException;
}
