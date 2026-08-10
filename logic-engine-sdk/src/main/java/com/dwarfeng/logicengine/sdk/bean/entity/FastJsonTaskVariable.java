package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.FastJsonTaskVariableKey;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 任务变量。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskVariable implements Bean {

    private static final long serialVersionUID = 7483529963472606336L;

    public static FastJsonTaskVariable of(TaskVariable taskVariable) {
        if (Objects.isNull(taskVariable)) {
            return null;
        } else {
            return new FastJsonTaskVariable(
                    FastJsonTaskVariableKey.of(taskVariable.getKey()),
                    taskVariable.getValueType(),
                    taskVariable.getStringValue(),
                    taskVariable.getLongValue(),
                    taskVariable.getDoubleValue(),
                    taskVariable.getBooleanValue(),
                    taskVariable.getDateValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskVariableKey key;

    @JSONField(name = "value_type", ordinal = 2)
    private int valueType;

    @JSONField(name = "string_value", ordinal = 3)
    private String stringValue;

    @JSONField(name = "long_value", ordinal = 4)
    private Long longValue;

    @JSONField(name = "double_value", ordinal = 5)
    private Double doubleValue;

    @JSONField(name = "boolean_value", ordinal = 6)
    private Boolean booleanValue;

    @JSONField(name = "date_value", ordinal = 7)
    private Date dateValue;

    public FastJsonTaskVariable() {
    }

    public FastJsonTaskVariable(
            FastJsonTaskVariableKey key, int valueType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue
    ) {
        this.key = key;
        this.valueType = valueType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
    }

    public FastJsonTaskVariableKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskVariableKey key) {
        this.key = key;
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

    @Override
    public String toString() {
        return "FastJsonTaskVariable{" +
                "key=" + key +
                ", valueType=" + valueType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                '}';
    }
}
