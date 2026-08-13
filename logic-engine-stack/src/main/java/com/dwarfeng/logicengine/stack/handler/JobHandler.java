package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.JobCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.JobCreateResult;
import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.concurrent.CompletableFuture;

/**
 * 作业处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface JobHandler extends Handler {

    /**
     * 创建作业。
     *
     * @param info 作业创建信息。
     * @return 作业创建结果。
     * @throws HandlerException 处理器异常。
     */
    JobCreateResult create(JobCreateInfo info) throws HandlerException;

    /**
     * 同步执行作业。
     *
     * @param info 作业执行信息。
     * @throws HandlerException 处理器异常。
     */
    void execute(JobExecuteInfo info) throws HandlerException;

    /**
     * 异步执行作业。
     *
     * @param info 作业执行信息。
     * @return 作业执行结果对应的 CompletableFuture。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<Void> executeAsync(JobExecuteInfo info) throws HandlerException;
}
