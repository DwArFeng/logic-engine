package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 任务变量查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskVariableInspectResult implements Dto {

    private static final long serialVersionUID = -8402772553712021786L;

    public static FastJsonTaskVariableInspectResult of(TaskVariableInspectResult taskVariableInspectResult) {
        if (Objects.isNull(taskVariableInspectResult)) {
            return null;
        } else {
            return new FastJsonTaskVariableInspectResult(
                    taskVariableInspectResult.getValueType(),
                    taskVariableInspectResult.getValue()
            );
        }
    }

    @JSONField(name = "value_type", ordinal = 1)
    private int valueType;

    @JSONField(name = "value", ordinal = 2, serialzeFeatures = SerializerFeature.WriteClassName)
    private Object value;

    public FastJsonTaskVariableInspectResult() {
    }

    public FastJsonTaskVariableInspectResult(int valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FastJsonTaskVariableInspectResult{" +
                "valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
