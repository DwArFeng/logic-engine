package com.dwarfeng.logicengine.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateTaskVariableKey implements Key {

    private static final long serialVersionUID = 2791126785652264182L;

    private Long taskLongId;
    private String variableStringId;

    public HibernateTaskVariableKey() {
    }

    public HibernateTaskVariableKey(Long taskLongId, String variableStringId) {
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

        HibernateTaskVariableKey that = (HibernateTaskVariableKey) o;
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
        return "HibernateTaskVariableKey{" +
                "taskLongId=" + taskLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
