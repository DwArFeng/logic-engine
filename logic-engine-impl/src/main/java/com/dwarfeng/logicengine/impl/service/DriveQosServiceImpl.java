package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.handler.DriveHandler;
import com.dwarfeng.logicengine.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.logicengine.stack.service.DriveQosService;
import com.dwarfeng.logicengine.stack.struct.DriveLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class DriveQosServiceImpl implements DriveQosService {

    private final DriveHandler driveHandler;
    private final DriveLocalCacheHandler driveLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    public DriveQosServiceImpl(
            DriveHandler driveHandler,
            DriveLocalCacheHandler driveLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.driveHandler = driveHandler;
        this.driveLocalCacheHandler = driveLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return driveHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断驱动处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public DriveLocalCache getDriveLocalCache(LongIdKey sectionKey) throws ServiceException {
        try {
            return driveLocalCacheHandler.get(sectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定部件的驱动本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            driveLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
