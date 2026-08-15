package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送处理器。
 *
 * <p>
 * 该处理器负责将逻辑引擎内部发生的任务生命周期、功能重置以及清除结果事件转换为外部可消费的推送事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PushHandler extends Handler {

    /**
     * 任务完成时执行的推送操作。
     *
     * @param section 相关的部件。
     * @throws HandlerException 处理器异常。
     */
    void taskFinished(Section section) throws HandlerException;

    /**
     * 任务失败时执行的推送操作。
     *
     * @param section 相关的部件。
     * @throws HandlerException 处理器异常。
     */
    void taskFailed(Section section) throws HandlerException;

    /**
     * 任务过期时执行的推送操作。
     *
     * @param section 相关的部件。
     * @throws HandlerException 处理器异常。
     */
    void taskExpired(Section section) throws HandlerException;

    /**
     * 任务死亡时执行的推送操作。
     *
     * @param section 相关的部件。
     * @throws HandlerException 处理器异常。
     */
    void taskDied(Section section) throws HandlerException;

    /**
     * 主管功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void superviseReset() throws HandlerException;

    /**
     * 作业功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void jobReset() throws HandlerException;

    /**
     * 清除完成时执行的推送操作。
     *
     * @param result 清除结束结果。
     * @throws HandlerException 处理器异常。
     */
    void purgeFinished(PurgeFinishedResult result) throws HandlerException;

    /**
     * 清除失败时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void purgeFailed() throws HandlerException;
}
