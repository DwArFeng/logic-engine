package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务变量查看信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskVariableInspectInfo implements Bean {

    private static final long serialVersionUID = -1087497046532386030L;

    public static TaskVariableInspectInfo toStackBean(WebInputTaskVariableInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskVariableInspectInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    webInput.getTaskVariableId()
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

    public WebInputTaskVariableInspectInfo() {
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

    @Override
    public String toString() {
        return "WebInputTaskVariableInspectInfo{" +
                "taskKey=" + taskKey +
                ", taskVariableId='" + taskVariableId + '\'' +
                '}';
    }
}
