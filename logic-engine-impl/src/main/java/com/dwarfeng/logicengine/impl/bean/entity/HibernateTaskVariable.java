package com.dwarfeng.logicengine.impl.bean.entity;

import com.dwarfeng.logicengine.impl.bean.key.HibernateTaskVariableKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(HibernateTaskVariableKey.class)
@Table(name = "tbl_task_variable")
public class HibernateTaskVariable implements Bean {

    private static final long serialVersionUID = -8800188992757758412L;

    // region 主键

    @Id
    @Column(name = "task_id", nullable = false)
    private Long taskLongId;

    @Id
    @Column(name = "variable_id", length = Constraints.LENGTH_STRING_ID, nullable = false)
    private String variableStringId;

    // endregion

    // region 主属性字段

    @Column(name = "value_type", nullable = false)
    private int valueType;

    @Column(name = "string_value", columnDefinition = "TEXT")
    private String stringValue;

    @Column(name = "long_value")
    private Long longValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "date_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateTask.class)
    @JoinColumns({ //
            @JoinColumn(name = "task_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateTask task;

    // endregion

    public HibernateTaskVariable() {
    }

    // region 映射用属性区

    public HibernateTaskVariableKey getKey() {
        if (java.util.Objects.isNull(taskLongId) || java.util.Objects.isNull(variableStringId)) {
            return null;
        }
        return new HibernateTaskVariableKey(taskLongId, variableStringId);
    }

    public void setKey(HibernateTaskVariableKey key) {
        if (java.util.Objects.isNull(key)) {
            this.taskLongId = null;
            this.variableStringId = null;
        } else {
            this.taskLongId = key.getTaskLongId();
            this.variableStringId = key.getVariableStringId();
        }
    }

    // endregion

    // region 常规属性区

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

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public HibernateTask getTask() {
        return task;
    }

    public void setTask(HibernateTask task) {
        this.task = task;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "taskLongId = " + taskLongId + ", " +
                "variableStringId = " + variableStringId + ", " +
                "valueType = " + valueType + ", " +
                "stringValue = " + stringValue + ", " +
                "longValue = " + longValue + ", " +
                "doubleValue = " + doubleValue + ", " +
                "booleanValue = " + booleanValue + ", " +
                "dateValue = " + dateValue + ", " +
                "task = " + task + ")";
    }
}
