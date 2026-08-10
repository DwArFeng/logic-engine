package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的任务变量值类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InvalidTaskVariableValueTypeException extends HandlerException {

    private static final long serialVersionUID = -7890485202768860425L;

    private final int valueType;

    public InvalidTaskVariableValueTypeException(int valueType) {
        this.valueType = valueType;
    }

    public InvalidTaskVariableValueTypeException(Throwable cause, int valueType) {
        super(cause);
        this.valueType = valueType;
    }

    @Override
    public String getMessage() {
        return "无效的任务变量值类型: " + valueType;
    }
}
