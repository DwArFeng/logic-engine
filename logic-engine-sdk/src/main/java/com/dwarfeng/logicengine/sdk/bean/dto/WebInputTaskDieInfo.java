package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskDieInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务死亡信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskDieInfo implements Bean {

    private static final long serialVersionUID = -2141288060188453354L;

    public static TaskDieInfo toStackBean(WebInputTaskDieInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskDieInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskDieInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskDieInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
