package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.exception.DispatcherException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 调度器。
 *
 * <p>
 * 调度器负责将部件执行请求分发给集群中的接收器，并保证请求在接收节点之间合理分布。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Dispatcher {

    /**
     * 返回调度器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 调度器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 启动调度器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws DispatcherException 调度器异常。
     */
    void start() throws DispatcherException;

    /**
     * 停止调度器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws DispatcherException 调度器异常。
     */
    void stop() throws DispatcherException;

    /**
     * 调度指定部件的执行请求。
     *
     * <p>
     * 调用该方法时需要判断调度器的启动状态，调度器未启动时应抛出异常。
     *
     * @param sectionKey 部件主键。
     * @throws DispatcherException 调度器异常。
     */
    void dispatch(LongIdKey sectionKey) throws DispatcherException;
}
