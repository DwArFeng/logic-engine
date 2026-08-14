package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.struct.DriveLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriveQosService extends Service {

    /**
     * 驱动服务是否启动。
     *
     * @return 驱动服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 获取指定部件的驱动本地缓存。
     *
     * @param sectionKey 指定部件的主键。
     * @return 指定部件的驱动本地缓存。
     * @throws ServiceException 服务异常。
     */
    DriveLocalCache getDriveLocalCache(LongIdKey sectionKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
