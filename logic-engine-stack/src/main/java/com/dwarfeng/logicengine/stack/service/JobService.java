package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 作业服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface JobService extends Service {

    /**
     * 执行作业。
     *
     * @param info 作业执行信息。
     * @throws ServiceException 服务异常。
     */
    void execute(JobExecuteInfo info) throws ServiceException;
}
