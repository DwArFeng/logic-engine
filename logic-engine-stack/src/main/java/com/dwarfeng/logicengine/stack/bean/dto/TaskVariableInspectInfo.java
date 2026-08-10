package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务变量查看信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableInspectInfo implements Dto {

    private static final long serialVersionUID = -2892077743086129293L;

    private LongIdKey taskKey;
    private String taskVariableId;

    public TaskVariableInspectInfo() {
    }

    public TaskVariableInspectInfo(LongIdKey taskKey, String taskVariableId) {
        this.taskKey = taskKey;
        this.taskVariableId = taskVariableId;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskVariableId() {
        return taskVariableId;
    }

    public void setTaskVariableId(String taskVariableId) {
        this.taskVariableId = taskVariableId;
    }

    @Override
    public String toString() {
        return "TaskVariableInspectInfo{" +
                "taskKey=" + taskKey +
                ", taskVariableId='" + taskVariableId + '\'' +
                '}';
    }
}
