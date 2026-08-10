package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskBeatInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务心跳信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskBeatInfo implements Bean {

    private static final long serialVersionUID = 3416641650962437178L;

    public static TaskBeatInfo toStackBean(WebInputTaskBeatInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskBeatInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskBeatInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskBeatInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
