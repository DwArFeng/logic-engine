package com.dwarfeng.logicengine.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 10000;

    public static final ServiceException.Code OPERATION_FAILED =
            new ServiceException.Code(offset(0), "operation failed");
    public static final ServiceException.Code TASK_NOT_EXISTS =
            new ServiceException.Code(offset(10), "task not exists");
    public static final ServiceException.Code TASK_VARIABLE_NOT_EXISTS =
            new ServiceException.Code(offset(20), "task variable not exists");
    public static final ServiceException.Code INVALID_TASK_VARIABLE_VALUE_TYPE =
            new ServiceException.Code(offset(30), "invalid task variable value type");
    public static final ServiceException.Code TASK_VARIABLE_VALUE_TYPE_MISMATCH =
            new ServiceException.Code(offset(40), "task variable value type mismatch");

    private static int offset(int value) {
        return EXCEPTION_CODE_OFFSET + value;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        OPERATION_FAILED.setCode(offset(0));
        TASK_NOT_EXISTS.setCode(offset(10));
        TASK_VARIABLE_NOT_EXISTS.setCode(offset(20));
        INVALID_TASK_VARIABLE_VALUE_TYPE.setCode(offset(30));
        TASK_VARIABLE_VALUE_TYPE_MISMATCH.setCode(offset(40));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
