package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.handler.SupportHandler;
import com.dwarfeng.logicengine.stack.service.SupportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class SupportQosServiceImpl implements SupportQosService {

    private final SupportHandler supportHandler;

    private final ServiceExceptionMapper sem;

    public SupportQosServiceImpl(SupportHandler supportHandler, ServiceExceptionMapper sem) {
        this.supportHandler = supportHandler;
        this.sem = sem;
    }

    @Override
    public void resetGuarder() throws ServiceException {
        try {
            supportHandler.resetGuarder();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置守卫器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetPerformer() throws ServiceException {
        try {
            supportHandler.resetPerformer();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置执行器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
