package com.dwarfeng.logicengine.sdk.util;

import com.dwarfeng.logicengine.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 logicengine 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(TaskNotExistsException.class, ServiceExceptionCodes.TASK_NOT_EXISTS);
        map.put(TaskVariableNotExistsException.class, ServiceExceptionCodes.TASK_VARIABLE_NOT_EXISTS);
        map.put(
                InvalidTaskVariableValueTypeException.class,
                ServiceExceptionCodes.INVALID_TASK_VARIABLE_VALUE_TYPE
        );
        map.put(
                TaskVariableValueTypeMismatchException.class,
                ServiceExceptionCodes.TASK_VARIABLE_VALUE_TYPE_MISMATCH
        );
        map.put(PerformerException.class, ServiceExceptionCodes.PERFORMER_FAILED);
        map.put(PerformerMakeException.class, ServiceExceptionCodes.PERFORMER_MAKE_FAILED);
        map.put(PerformerExecutionException.class, ServiceExceptionCodes.PERFORMER_EXECUTION_FAILED);
        map.put(UnsupportedPerformerTypeException.class, ServiceExceptionCodes.PERFORMER_TYPE_UNSUPPORTED);
        map.put(GuarderException.class, ServiceExceptionCodes.GUARDER_FAILED);
        map.put(GuarderMakeException.class, ServiceExceptionCodes.GUARDER_MAKE_FAILED);
        map.put(GuarderExecutionException.class, ServiceExceptionCodes.GUARDER_EXECUTION_FAILED);
        map.put(UnsupportedGuarderTypeException.class, ServiceExceptionCodes.GUARDER_TYPE_UNSUPPORTED);
        map.put(SectionNotExistsException.class, ServiceExceptionCodes.SECTION_NOT_EXISTS);
        map.put(TaskStatusMismatchException.class, ServiceExceptionCodes.TASK_STATUS_MISMATCH);
        map.put(InvalidTaskStatusException.class, ServiceExceptionCodes.INVALID_TASK_STATUS);
        map.put(ReceiverException.class, ServiceExceptionCodes.RECEIVER_FAILED);
        map.put(ReceiverNotStartException.class, ServiceExceptionCodes.RECEIVER_NOT_START);
        map.put(ReceiverExecutionException.class, ServiceExceptionCodes.RECEIVER_EXECUTION_FAILED);
        map.put(DispatcherException.class, ServiceExceptionCodes.DISPATCHER_FAILED);
        map.put(DispatcherNotStartException.class, ServiceExceptionCodes.DISPATCHER_NOT_START);
        map.put(DispatcherExecutionException.class, ServiceExceptionCodes.DISPATCHER_EXECUTION_FAILED);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
