package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.logicengine.stack.handler.ManualDispatchHandler;
import com.dwarfeng.logicengine.stack.service.ManualDispatchQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 手动调度 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
@Service
public class ManualDispatchQosServiceImpl implements ManualDispatchQosService {

    private final ManualDispatchHandler manualDispatchHandler;

    private final ServiceExceptionMapper sem;

    public ManualDispatchQosServiceImpl(ManualDispatchHandler manualDispatchHandler, ServiceExceptionMapper sem) {
        this.manualDispatchHandler = manualDispatchHandler;
        this.sem = sem;
    }

    @Override
    public void dispatch(ManualDispatchInfo info) throws ServiceException {
        try {
            manualDispatchHandler.dispatch(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("手动调度部件时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
