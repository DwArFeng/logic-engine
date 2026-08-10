package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 任务事件创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskEventCreateInfo implements Dto {

    private static final long serialVersionUID = 2842594467119492052L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    /**
     * 发生时间。
     */
    private Date happenedDate;

    /**
     * 事件消息。
     */
    private String message;

    public TaskEventCreateInfo() {
    }

    public TaskEventCreateInfo(LongIdKey taskKey, Date happenedDate, String message) {
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
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
        return "TaskEventCreateInfo{" +
                "taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message=" + message +
                '}';
    }
}
