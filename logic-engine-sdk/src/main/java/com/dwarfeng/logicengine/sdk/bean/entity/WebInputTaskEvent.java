package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskEvent implements Bean {

    private static final long serialVersionUID = -2345149407639218628L;

    public static TaskEvent toStackBean(WebInputTaskEvent webInputTaskEvent) {
        if (Objects.isNull(webInputTaskEvent)) {
            return null;
        } else {
            return new TaskEvent(
                    WebInputLongIdKey.toStackBean(webInputTaskEvent.getKey()),
                    WebInputLongIdKey.toStackBean(webInputTaskEvent.getTaskKey()),
                    webInputTaskEvent.getHappenedDate(),
                    webInputTaskEvent.getMessage()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "task_key")
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "happened_date")
    @NotNull
    private Date happenedDate;

    @JSONField(name = "message")
    private String message;

    public WebInputTaskEvent() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
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
        return "WebInputTaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
