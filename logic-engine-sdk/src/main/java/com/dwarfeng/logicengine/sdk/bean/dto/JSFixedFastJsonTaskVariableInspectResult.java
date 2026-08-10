package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 任务变量查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskVariableInspectResult implements Dto {

    private static final long serialVersionUID = 4329301613355616446L;

    public static JSFixedFastJsonTaskVariableInspectResult of(TaskVariableInspectResult taskVariableInspectResult) {
        if (Objects.isNull(taskVariableInspectResult)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskVariableInspectResult(
                    taskVariableInspectResult.getValueType(),
                    taskVariableInspectResult.getValue()
            );
        }
    }

    @JSONField(name = "value_type", ordinal = 1)
    private int valueType;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    public JSFixedFastJsonTaskVariableInspectResult() {
    }

    public JSFixedFastJsonTaskVariableInspectResult(int valueType, Object value) {
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
        return "JSFixedFastJsonTaskVariableInspectResult{" +
                "valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
