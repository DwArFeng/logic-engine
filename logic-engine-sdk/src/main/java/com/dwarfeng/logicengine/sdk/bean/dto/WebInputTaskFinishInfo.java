package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskFinishInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务完成信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskFinishInfo implements Bean {

    private static final long serialVersionUID = -8412088423681792359L;

    public static TaskFinishInfo toStackBean(WebInputTaskFinishInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskFinishInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskFinishInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskFinishInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
