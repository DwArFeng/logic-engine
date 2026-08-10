package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskFailInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务失败信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskFailInfo implements Bean {

    private static final long serialVersionUID = -3077619777660786184L;

    public static TaskFailInfo toStackBean(WebInputTaskFailInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskFailInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskFailInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskFailInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
