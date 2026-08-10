package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 任务不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskNotExistsException extends HandlerException {

    private static final long serialVersionUID = 5478622883998948189L;

    private final LongIdKey taskKey;

    public TaskNotExistsException(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public TaskNotExistsException(Throwable cause, LongIdKey taskKey) {
        super(cause);
        this.taskKey = taskKey;
    }

    @Override
    public String getMessage() {
        return "任务 " + taskKey + " 不存在";
    }
}
