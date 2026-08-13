package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.logicengine.stack.handler.JobHandler;
import com.dwarfeng.logicengine.stack.service.JobService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private final JobHandler jobHandler;

    private final ServiceExceptionMapper sem;

    public JobServiceImpl(JobHandler jobHandler, ServiceExceptionMapper sem) {
        this.jobHandler = jobHandler;
        this.sem = sem;
    }

    @Override
    public void execute(JobExecuteInfo info) throws ServiceException {
        try {
            jobHandler.execute(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("执行作业时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
