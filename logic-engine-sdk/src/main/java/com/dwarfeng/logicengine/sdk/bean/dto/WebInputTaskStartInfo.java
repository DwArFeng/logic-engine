package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskStartInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务启动信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskStartInfo implements Bean {

    private static final long serialVersionUID = 5812956924283118544L;

    public static TaskStartInfo toStackBean(WebInputTaskStartInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskStartInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskStartInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskStartInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
