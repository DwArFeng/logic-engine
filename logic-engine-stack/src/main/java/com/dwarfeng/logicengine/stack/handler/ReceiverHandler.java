package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 接收器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReceiverHandler extends Handler {

    /**
     * 返回当前正在使用的接收器。
     *
     * @return 当前正在使用的接收器。
     * @throws HandlerException 处理器异常。
     */
    Receiver current() throws HandlerException;

    /**
     * 返回所有的接收器。
     *
     * @return 所有的接收器组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<Receiver> all() throws HandlerException;
}
