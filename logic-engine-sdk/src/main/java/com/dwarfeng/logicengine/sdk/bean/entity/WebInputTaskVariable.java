package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputTaskVariableKey;
import com.dwarfeng.logicengine.sdk.util.ValidTaskVariableValueType;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 任务变量。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskVariable implements Bean {

    private static final long serialVersionUID = -422510359805246676L;

    public static TaskVariable toStackBean(WebInputTaskVariable webInputTaskVariable) {
        if (Objects.isNull(webInputTaskVariable)) {
            return null;
        } else {
            return new TaskVariable(
                    WebInputTaskVariableKey.toStackBean(webInputTaskVariable.getKey()),
                    webInputTaskVariable.getValueType(),
                    webInputTaskVariable.getStringValue(),
                    webInputTaskVariable.getLongValue(),
                    webInputTaskVariable.getDoubleValue(),
                    webInputTaskVariable.getBooleanValue(),
                    webInputTaskVariable.getDateValue()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputTaskVariableKey key;

    @JSONField(name = "value_type")
    @ValidTaskVariableValueType
    private int valueType;

    @JSONField(name = "string_value")
    private String stringValue;

    @JSONField(name = "long_value")
    private Long longValue;

    @JSONField(name = "double_value")
    private Double doubleValue;

    @JSONField(name = "boolean_value")
    private Boolean booleanValue;

    @JSONField(name = "date_value")
    private Date dateValue;

    public WebInputTaskVariable() {
    }

    public WebInputTaskVariableKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskVariableKey key) {
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
        return "WebInputTaskVariable{" +
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
