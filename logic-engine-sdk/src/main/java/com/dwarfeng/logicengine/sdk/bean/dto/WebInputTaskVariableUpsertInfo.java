package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.sdk.util.ValidTaskVariableValueType;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskVariableUpsertInfo implements Bean {

    private static final long serialVersionUID = -5116446654052925986L;

    public static TaskVariableUpsertInfo toStackBean(WebInputTaskVariableUpsertInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskVariableUpsertInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    webInput.getTaskVariableId(),
                    webInput.getValueType(),
                    webInput.getValue()
            );
        }
    }

    @JSONField(name = "task_key")
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "task_variable_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String taskVariableId;

    @JSONField(name = "value_type")
    @ValidTaskVariableValueType
    private int valueType;

    /**
     * 任务变量值。
     *
     * <p>
     * 需要注意的是，许多序列化工具并不能很好地支持 Object 类型的字段的序列化与反序列化，
     * 因此在使用该字段时需要注意可能出现的问题。
     *
     * <p>
     * 如果有必要，建议开发人员为自己的项目定制专门的 WebInput 类，并应用明确的类型和转换规则，
     * 以避免使用通用的 Object 类型所带来的潜在问题。
     */
    @JSONField(name = "value")
    private Object value;

    public WebInputTaskVariableUpsertInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskVariableId() {
        return taskVariableId;
    }

    public void setTaskVariableId(String taskVariableId) {
        this.taskVariableId = taskVariableId;
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
        return "WebInputTaskVariableUpsertInfo{" +
                "taskKey=" + taskKey +
                ", taskVariableId='" + taskVariableId + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
