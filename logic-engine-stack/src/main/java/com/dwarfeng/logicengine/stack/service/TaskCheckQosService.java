package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务检查 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskCheckQosService extends Service {

    /**
     * 任务检查服务是否上线。
     *
     * @return 是否上线。
     * @throws ServiceException 服务异常。
     */
    boolean isOnline() throws ServiceException;

    /**
     * 上线任务检查服务。
     *
     * @throws ServiceException 服务异常。
     */
    void online() throws ServiceException;

    /**
     * 下线任务检查服务。
     *
     * @throws ServiceException 服务异常。
     */
    void offline() throws ServiceException;

    /**
     * 任务检查服务是否正在持有锁。
     *
     * @return 任务检查服务是否正在持有锁。
     * @throws ServiceException 服务异常。
     */
    boolean isLockHolding() throws ServiceException;

    /**
     * 任务检查服务是否启动。
     *
     * @return 任务检查服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 启动任务检查服务。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 停止任务检查服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 任务检查服务是否正在工作。
     *
     * @return 任务检查服务是否正在工作。
     * @throws ServiceException 服务异常。
     */
    boolean isWorking() throws ServiceException;
}
