package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.dutil.basic.cls.ClassUtil;
import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.exception.InvalidTaskVariableValueTypeException;
import com.dwarfeng.logicengine.stack.exception.TaskNotExistsException;
import com.dwarfeng.logicengine.stack.exception.TaskVariableNotExistsException;
import com.dwarfeng.logicengine.stack.exception.TaskVariableValueTypeMismatchException;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskVariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class HandlerValidator {

    private final TaskMaintainService taskMaintainService;
    private final TaskVariableMaintainService taskVariableMaintainService;

    public HandlerValidator(
            TaskMaintainService taskMaintainService,
            TaskVariableMaintainService taskVariableMaintainService
    ) {
        this.taskMaintainService = taskMaintainService;
        this.taskVariableMaintainService = taskVariableMaintainService;
    }

    public void makeSureTaskExists(LongIdKey taskKey) throws HandlerException {
        try {
            if (!taskMaintainService.exists(taskKey)) {
                throw new TaskNotExistsException(taskKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureTaskVariableExists(TaskVariableKey taskVariableKey) throws HandlerException {
        try {
            if (!taskVariableMaintainService.exists(taskVariableKey)) {
                throw new TaskVariableNotExistsException(taskVariableKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureTaskVariableValueTypeValid(int valueType, Object value) throws HandlerException {
        if (!Constants.taskVariableValueTypeSpace().contains(valueType)) {
            throw new InvalidTaskVariableValueTypeException(valueType);
        }
        if (Objects.isNull(value)) {
            return;
        }
        Class<?> expectedValueClazz;
        switch (valueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                expectedValueClazz = String.class;
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                expectedValueClazz = Long.class;
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                expectedValueClazz = Double.class;
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                expectedValueClazz = Boolean.class;
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                expectedValueClazz = Date.class;
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
        // 获取 value 的实际类型，特别地，如果 actualValueClazz 是基本类型，则转换为对应的包装类型。
        Class<?> actualValueClazz = ClassUtil.getPackedClass(value.getClass());
        // 如果 actualValueClazz 不是 expectedValueClazz 或 expectedValueClazz 的子类，则抛出异常。
        if (!expectedValueClazz.isAssignableFrom(actualValueClazz)) {
            throw new TaskVariableValueTypeMismatchException(valueType, expectedValueClazz, actualValueClazz);
        }
    }
}
