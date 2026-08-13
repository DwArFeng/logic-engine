package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 作业 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface JobQosService extends Service {

    /**
     * 获取指定部件的作业本地缓存。
     *
     * @param sectionKey 部件主键。
     * @return 指定部件的作业本地缓存。
     * @throws ServiceException 服务异常。
     */
    JobLocalCache getJobLocalCache(LongIdKey sectionKey) throws ServiceException;

    /**
     * 清除全部作业本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 手动创建并执行指定部件的作业。
     *
     * @param sectionKey 指定部件的主键。
     * @throws ServiceException 服务异常。
     */
    void execute(LongIdKey sectionKey) throws ServiceException;
}
