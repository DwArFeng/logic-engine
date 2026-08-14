package com.dwarfeng.logicengine.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 逻辑引擎常量。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

    @StateTypeItem
    public static final int STATE_TYPE_INITIAL = 0;
    @StateTypeItem
    public static final int STATE_TYPE_NORMAL = 1;
    @StateTypeItem
    public static final int STATE_TYPE_TERMINAL = 2;

    @TaskStatusItem
    public static final int TASK_STATUS_CREATED = 0;
    @TaskStatusItem
    public static final int TASK_STATUS_PROCESSING = 1;
    @TaskStatusItem
    public static final int TASK_STATUS_FINISHED = 2;
    @TaskStatusItem
    public static final int TASK_STATUS_FAILED = 3;
    @TaskStatusItem
    public static final int TASK_STATUS_EXPIRED = 4;
    @TaskStatusItem
    public static final int TASK_STATUS_DIED = 5;

    @TaskVariableValueTypeItem
    public static final int TASK_VARIABLE_VALUE_TYPE_STRING = 0;
    @TaskVariableValueTypeItem
    public static final int TASK_VARIABLE_VALUE_TYPE_LONG = 1;
    @TaskVariableValueTypeItem
    public static final int TASK_VARIABLE_VALUE_TYPE_DOUBLE = 2;
    @TaskVariableValueTypeItem
    public static final int TASK_VARIABLE_VALUE_TYPE_BOOLEAN = 3;
    @TaskVariableValueTypeItem
    public static final int TASK_VARIABLE_VALUE_TYPE_DATE = 4;

    /**
     * 消费者处理器的检查间隔。
     */
    public static final long CONSUMER_HANDLER_CHECK_INTERVAL = 5000L;

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> stateTypeSpace;
    private static List<Integer> taskStatusSpace;
    private static List<Integer> taskVariableValueTypeSpace;

    public static List<Integer> stateTypeSpace() {
        if (Objects.nonNull(stateTypeSpace)) {
            return stateTypeSpace;
        }
        LOCK.lock();
        try {
            if (Objects.isNull(stateTypeSpace)) {
                stateTypeSpace = initSpace(StateTypeItem.class);
            }
            return stateTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    public static List<Integer> taskStatusSpace() {
        if (Objects.nonNull(taskStatusSpace)) {
            return taskStatusSpace;
        }
        LOCK.lock();
        try {
            if (Objects.isNull(taskStatusSpace)) {
                taskStatusSpace = initSpace(TaskStatusItem.class);
            }
            return taskStatusSpace;
        } finally {
            LOCK.unlock();
        }
    }

    public static List<Integer> taskVariableValueTypeSpace() {
        if (Objects.nonNull(taskVariableValueTypeSpace)) {
            return taskVariableValueTypeSpace;
        }
        LOCK.lock();
        try {
            if (Objects.isNull(taskVariableValueTypeSpace)) {
                taskVariableValueTypeSpace = initSpace(TaskVariableValueTypeItem.class);
            }
            return taskVariableValueTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static List<Integer> initSpace(Class<? extends java.lang.annotation.Annotation> itemClass) {
        List<Integer> result = new ArrayList<>();
        for (Field declaredField : Constants.class.getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(itemClass)) {
                continue;
            }
            try {
                result.add((Integer) declaredField.get(null));
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }
        return Collections.unmodifiableList(result);
    }

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
