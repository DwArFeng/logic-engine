package com.dwarfeng.logicengine.sdk.handler;

import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 事件推送器。
 *
 * <p>
 * 推送器是推送机制的可插拔实现。每个推送器通过类型标识参与装配，由推送处理器选择当前配置的具体实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Pusher {

    /**
     * 返回推送器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 推送器是否支持指定的类型。
     */
    boolean supportType(String type);

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
