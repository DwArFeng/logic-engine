package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 任务变量不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableNotExistsException extends HandlerException {

    private static final long serialVersionUID = 5390954561959250437L;

    private final TaskVariableKey taskVariableKey;

    public TaskVariableNotExistsException(TaskVariableKey taskVariableKey) {
        this.taskVariableKey = taskVariableKey;
    }

    public TaskVariableNotExistsException(Throwable cause, TaskVariableKey taskVariableKey) {
        super(cause);
        this.taskVariableKey = taskVariableKey;
    }

    @Override
    public String getMessage() {
        return "任务变量 " + taskVariableKey + " 不存在";
    }
}
