package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskEvent implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6762317927743735215L;

    private LongIdKey key;

    /**
     * 所属任务。
     */
    private LongIdKey taskKey;

    /**
     * 事件发生时间。
     */
    private Date happenedDate;

    /**
     * 事件消息。
     */
    private String message;

    public TaskEvent() {
    }

    public TaskEvent(LongIdKey key, LongIdKey taskKey, Date happenedDate, String message) {
        this.key = key;
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
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
        return "TaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
