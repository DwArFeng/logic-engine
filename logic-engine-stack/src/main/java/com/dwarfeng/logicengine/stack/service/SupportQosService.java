package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SupportQosService extends Service {

    /**
     * 重置守卫器。
     *
     * @throws ServiceException 服务异常。
     * @since 1.0.0
     */
    void resetGuarder() throws ServiceException;

    /**
     * 重置执行器。
     *
     * @throws ServiceException 服务异常。
     * @since 1.0.0
     */
    void resetPerformer() throws ServiceException;
}
