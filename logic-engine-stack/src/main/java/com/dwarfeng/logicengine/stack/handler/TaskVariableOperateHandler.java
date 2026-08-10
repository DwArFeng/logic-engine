package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableRemoveInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nullable;

/**
 * 任务变量操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskVariableOperateHandler extends Handler {

    /**
     * 查看任务变量。
     *
     * <p>
     * 该方法返回指定的任务变量查看信息对应的任务变量的查看结果。<br>
     * 如果指定的任务变量不存在，则返回 <code>null</code>。
     *
     * @param info 任务变量查看信息。
     * @return 任务变量的查看结果。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    TaskVariableInspectResult inspect(TaskVariableInspectInfo info) throws HandlerException;

    /**
     * 插入/更新任务变量。
     *
     * @param info 任务变量插入/更新信息。
     * @throws HandlerException 处理器异常。
     */
    void upsert(TaskVariableUpsertInfo info) throws HandlerException;

    /**
     * 删除任务变量。
     *
     * @param info 任务变量删除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(TaskVariableRemoveInfo info) throws HandlerException;
}
