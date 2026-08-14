package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.struct.ConsumeInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ConsumeHandler extends StartableHandler {

    /**
     * 使消费处理器接受指定的消费信息。
     *
     * @param consumeInfo 指定的消费信息。
     * @throws HandlerException 处理器异常。
     */
    void accept(ConsumeInfo consumeInfo) throws HandlerException;

    /**
     * 获取缓冲器已经缓冲的容量。
     *
     * @return 缓冲器已经缓冲的容量。
     * @throws HandlerException 处理器异常。
     */
    int bufferedSize() throws HandlerException;

    /**
     * 获取缓冲器的容量。
     *
     * @return 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    int getBufferSize() throws HandlerException;

    /**
     * 设置缓冲器的容量。
     *
     * @param size 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    void setBufferSize(int size) throws HandlerException;

    /**
     * 获取消费者的线程数量。
     *
     * @return 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    int getThread() throws HandlerException;

    /**
     * 设置消费者的线程数量。
     *
     * @param thread 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setThread(int thread) throws HandlerException;

    /**
     * 获取消费者是否空闲。
     *
     * @return 消费者是否空闲。
     * @throws HandlerException 处理器异常。
     */
    boolean isIdle() throws HandlerException;
}
