package com.dwarfeng.logicengine.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskVariableKey implements Key {

    private static final long serialVersionUID = -9006533650580195193L;

    public static TaskVariableKey toStackBean(WebInputTaskVariableKey webInputTaskVariableKey) {
        if (Objects.isNull(webInputTaskVariableKey)) {
            return null;
        } else {
            return new TaskVariableKey(
                    webInputTaskVariableKey.getTaskLongId(),
                    webInputTaskVariableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "task_long_id")
    @NotNull
    private Long taskLongId;

    @JSONField(name = "variable_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String variableStringId;

    public WebInputTaskVariableKey() {
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

        WebInputTaskVariableKey that = (WebInputTaskVariableKey) o;
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
        return "WebInputTaskVariableKey{" +
                "taskLongId=" + taskLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
