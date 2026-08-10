package com.dwarfeng.logicengine.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 任务变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskVariableKey implements Key {

    private static final long serialVersionUID = 3438654653328175204L;

    public static JSFixedFastJsonTaskVariableKey of(TaskVariableKey taskVariableKey) {
        if (Objects.isNull(taskVariableKey)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskVariableKey(
                    taskVariableKey.getTaskLongId(),
                    taskVariableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "task_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long taskLongId;

    @JSONField(name = "variable_string_id", ordinal = 2)
    private String variableStringId;

    public JSFixedFastJsonTaskVariableKey() {
    }

    public JSFixedFastJsonTaskVariableKey(Long taskLongId, String variableStringId) {
        this.taskLongId = taskLongId;
        this.variableStringId = variableStringId;
    }

    public Long getTaskLongId() {
        return taskLongId;
    }

    public void setTaskLongId(Long taskLongId) {
        this.taskLongId = taskLongId;
    }

    public String getVariableStringId() {
        return variableStringId;
    }

    public void setVariableStringId(String variableStringId) {
        this.variableStringId = variableStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        JSFixedFastJsonTaskVariableKey that = (JSFixedFastJsonTaskVariableKey) o;
        return Objects.equals(taskLongId, that.taskLongId)
                && Objects.equals(variableStringId, that.variableStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(taskLongId);
        result = 31 * result + Objects.hashCode(variableStringId);
        return result;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonTaskVariableKey{" +
                "taskLongId=" + taskLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
