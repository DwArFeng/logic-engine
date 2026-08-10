package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 任务事件操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventOperateHandler extends Handler {

    /**
     * 创建任务事件。
     *
     * @param info 任务事件创建信息。
     * @return 任务事件创建结果。
     * @throws HandlerException 处理器异常。
     */
    TaskEventCreateResult create(TaskEventCreateInfo info) throws HandlerException;
}
