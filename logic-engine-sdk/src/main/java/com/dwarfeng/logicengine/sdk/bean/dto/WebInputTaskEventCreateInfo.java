package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 任务事件创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskEventCreateInfo implements Bean {

    private static final long serialVersionUID = -7577946098842344653L;

    public static TaskEventCreateInfo toStackBean(WebInputTaskEventCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskEventCreateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    webInput.getHappenedDate(),
                    webInput.getMessage()
            );
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "happened_date", ordinal = 2)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 3)
    private String message;

    public WebInputTaskEventCreateInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebInputTaskEventCreateInfo{" +
                "taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message=" + message +
                '}';
    }
}
