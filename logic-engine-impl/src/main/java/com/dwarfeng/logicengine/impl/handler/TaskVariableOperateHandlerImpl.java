package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableRemoveInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.handler.TaskVariableOperateHandler;
import com.dwarfeng.logicengine.stack.service.TaskVariableMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

@Component
public class TaskVariableOperateHandlerImpl implements TaskVariableOperateHandler {

    private final TaskVariableMaintainService taskVariableMaintainService;

    private final HandlerValidator handlerValidator;

    public TaskVariableOperateHandlerImpl(
            TaskVariableMaintainService taskVariableMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.taskVariableMaintainService = taskVariableMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Nullable
    @Override
    public TaskVariableInspectResult inspect(TaskVariableInspectInfo info) throws HandlerException {
        try {
            return inspect0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private TaskVariableInspectResult inspect0(TaskVariableInspectInfo info) throws Exception {
        // 展开参数。
        LongIdKey taskKey = info.getTaskKey();
        String taskVariableId = info.getTaskVariableId();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);

        // 调用维护服务获取任务变量。
        TaskVariableKey taskVariableKey = new TaskVariableKey(taskKey.getLongId(), taskVariableId);
        TaskVariable taskVariable = taskVariableMaintainService.getIfExists(taskVariableKey);

        // 如果任务变量不存在，则返回 null。
        if (Objects.isNull(taskVariable)) {
            return null;
        }

        // 构建返回值并返回。
        int valueType = taskVariable.getValueType();
        Object value;
        switch (valueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                value = taskVariable.getStringValue();
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                value = taskVariable.getLongValue();
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                value = taskVariable.getDoubleValue();
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                value = taskVariable.getBooleanValue();
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                value = taskVariable.getDateValue();
                break;
            default:
                throw new IllegalStateException("非法的 valueType 值: " + valueType);
        }
        return new TaskVariableInspectResult(valueType, value);
    }

    @Override
    public void upsert(TaskVariableUpsertInfo info) throws HandlerException {
        try {
            upsert0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void upsert0(TaskVariableUpsertInfo info) throws Exception {
        // 展开参数。
        LongIdKey taskKey = info.getTaskKey();
        String taskVariableId = info.getTaskVariableId();
        int valueType = info.getValueType();
        Object value = info.getValue();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务变量值类型有效。
        handlerValidator.makeSureTaskVariableValueTypeValid(valueType, value);

        // 构建任务变量。
        TaskVariable taskVariable = new TaskVariable(
                new TaskVariableKey(taskKey.getLongId(), taskVariableId),
                valueType, null, null, null, null, null
        );
        if (Objects.nonNull(value)) {
            switch (valueType) {
                case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                    taskVariable.setStringValue((String) value);
                    break;
                case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                    taskVariable.setLongValue((Long) value);
                    break;
                case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                    taskVariable.setDoubleValue((Double) value);
                    break;
                case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                    taskVariable.setBooleanValue((Boolean) value);
                    break;
                case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                    taskVariable.setDateValue((Date) value);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        }

        // 调用维护服务插入/更新任务变量。
        taskVariableMaintainService.insertOrUpdate(taskVariable);
    }

    @Override
    public void remove(TaskVariableRemoveInfo info) throws HandlerException {
        try {
            remove0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void remove0(TaskVariableRemoveInfo info) throws Exception {
        // 展开参数。
        LongIdKey taskKey = info.getTaskKey();
        String taskVariableId = info.getTaskVariableId();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务变量存在。
        TaskVariableKey taskVariableKey = new TaskVariableKey(taskKey.getLongId(), taskVariableId);
        handlerValidator.makeSureTaskVariableExists(taskVariableKey);

        // 调用维护服务删除任务变量。
        taskVariableMaintainService.delete(taskVariableKey);
    }
}
