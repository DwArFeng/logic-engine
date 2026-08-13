package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.dto.JobCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.JobCreateResult;
import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.logicengine.stack.handler.JobHandler;
import com.dwarfeng.logicengine.stack.handler.JobLocalCacheHandler;
import com.dwarfeng.logicengine.stack.service.JobQosService;
import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class JobQosServiceImpl implements JobQosService {

    private final JobLocalCacheHandler jobLocalCacheHandler;
    private final JobHandler jobHandler;
    private final ServiceExceptionMapper sem;

    public JobQosServiceImpl(
            JobLocalCacheHandler jobLocalCacheHandler,
            JobHandler jobHandler,
            ServiceExceptionMapper sem
    ) {
        this.jobLocalCacheHandler = jobLocalCacheHandler;
        this.jobHandler = jobHandler;
        this.sem = sem;
    }

    @Override
    public JobLocalCache getJobLocalCache(LongIdKey sectionKey) throws ServiceException {
        try {
            return jobLocalCacheHandler.get(sectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定部件的作业本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            jobLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除作业本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void execute(LongIdKey sectionKey) throws ServiceException {
        try {
            JobCreateResult jobCreateResult = jobHandler.create(new JobCreateInfo(sectionKey));
            jobHandler.execute(new JobExecuteInfo(jobCreateResult.getTaskKey()));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("手动创建并执行指定部件作业时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
