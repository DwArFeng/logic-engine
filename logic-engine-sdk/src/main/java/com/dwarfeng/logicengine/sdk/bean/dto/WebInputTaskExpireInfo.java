package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskExpireInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务过期信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskExpireInfo implements Bean {

    private static final long serialVersionUID = 4216770410081652591L;

    public static TaskExpireInfo toStackBean(WebInputTaskExpireInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskExpireInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputTaskExpireInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskExpireInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
