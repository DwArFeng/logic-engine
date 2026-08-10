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

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
