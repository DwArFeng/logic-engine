package com.dwarfeng.logicengine.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 任务变量值类型不匹配异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableValueTypeMismatchException extends HandlerException {

    private static final long serialVersionUID = -4713731403581353088L;

    private final int valueType;
    private final Class<?> expectedValueClazz;
    private final Class<?> actualValueClazz;

    public TaskVariableValueTypeMismatchException(
            int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz
    ) {
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    public TaskVariableValueTypeMismatchException(
            Throwable cause, int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz
    ) {
        super(cause);
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    @Override
    public String getMessage() {
        return "任务变量值类型不匹配, 任务变量值类型为 " + valueType + ", 期望的值类型为 " + expectedValueClazz +
                ", 实际的值类型为 " + actualValueClazz;
    }
}
