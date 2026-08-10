package com.dwarfeng.logicengine.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 任务变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableKey implements Key {

    private static final long serialVersionUID = -8974325987166318782L;

    /**
     * 任务主键。
     */
    private Long taskLongId;

    /**
     * 变量标识。
     */
    private String variableStringId;

    public TaskVariableKey() {
    }

    public TaskVariableKey(Long taskLongId, String variableStringId) {
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

        TaskVariableKey that = (TaskVariableKey) o;
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
        return "TaskVariableKey{" +
                "taskLongId=" + taskLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
