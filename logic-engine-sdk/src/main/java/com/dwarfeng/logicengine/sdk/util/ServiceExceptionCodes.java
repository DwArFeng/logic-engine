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
    public static final ServiceException.Code PERFORMER_FAILED =
            new ServiceException.Code(offset(50), "performer failed");
    public static final ServiceException.Code PERFORMER_MAKE_FAILED =
            new ServiceException.Code(offset(51), "performer make failed");
    public static final ServiceException.Code PERFORMER_EXECUTION_FAILED =
            new ServiceException.Code(offset(52), "performer execution failed");
    public static final ServiceException.Code PERFORMER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(53), "performer type unsupported");
    public static final ServiceException.Code GUARDER_FAILED =
            new ServiceException.Code(offset(60), "guarder failed");
    public static final ServiceException.Code GUARDER_MAKE_FAILED =
            new ServiceException.Code(offset(61), "guarder make failed");
    public static final ServiceException.Code GUARDER_EXECUTION_FAILED =
            new ServiceException.Code(offset(62), "guarder execution failed");
    public static final ServiceException.Code GUARDER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(63), "guarder type unsupported");
    public static final ServiceException.Code SECTION_NOT_EXISTS =
            new ServiceException.Code(offset(70), "section not exists");
    public static final ServiceException.Code TASK_STATUS_MISMATCH =
            new ServiceException.Code(offset(80), "task status mismatch");
    public static final ServiceException.Code INVALID_TASK_STATUS =
            new ServiceException.Code(offset(90), "invalid task status");
    public static final ServiceException.Code RECEIVER_FAILED =
            new ServiceException.Code(offset(100), "receiver failed");
    public static final ServiceException.Code RECEIVER_NOT_START =
            new ServiceException.Code(offset(101), "receiver not start");
    public static final ServiceException.Code RECEIVER_EXECUTION_FAILED =
            new ServiceException.Code(offset(102), "receiver execution failed");

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
        PERFORMER_FAILED.setCode(offset(50));
        PERFORMER_MAKE_FAILED.setCode(offset(51));
        PERFORMER_EXECUTION_FAILED.setCode(offset(52));
        PERFORMER_TYPE_UNSUPPORTED.setCode(offset(53));
        GUARDER_FAILED.setCode(offset(60));
        GUARDER_MAKE_FAILED.setCode(offset(61));
        GUARDER_EXECUTION_FAILED.setCode(offset(62));
        GUARDER_TYPE_UNSUPPORTED.setCode(offset(63));
        SECTION_NOT_EXISTS.setCode(offset(70));
        TASK_STATUS_MISMATCH.setCode(offset(80));
        INVALID_TASK_STATUS.setCode(offset(90));
        RECEIVER_FAILED.setCode(offset(100));
        RECEIVER_NOT_START.setCode(offset(101));
        RECEIVER_EXECUTION_FAILED.setCode(offset(102));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
