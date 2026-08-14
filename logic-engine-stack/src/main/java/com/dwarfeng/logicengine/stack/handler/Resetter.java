package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Resetter {

    /**
     * 初始化重置器。
     *
     * <p>
     * 该方法会在重置器初始化后调用。<br>
     * 该方法会传入一个 {@link Context}，此对象为重置器的上下文，其中包含了重置器需要使用的所有方法。<br>
     * 实现该方法时，应妥善保存上下文，以便在后续的方法调用中使用。
     *
     * @param context 重置器的上下文。
     */
    void init(Context context);

    /**
     * 启动重置器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 停止重置器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 重置器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 重置主管功能。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         */
        void resetSupervise() throws Exception;

        /**
         * 重置作业功能。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         */
        void resetJob() throws Exception;
    }
}
