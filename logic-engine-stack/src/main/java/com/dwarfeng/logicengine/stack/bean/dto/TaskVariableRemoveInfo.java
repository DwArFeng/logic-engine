package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务变量删除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableRemoveInfo implements Dto {

    private static final long serialVersionUID = -2825497055643903692L;

    private LongIdKey taskKey;
    private String taskVariableId;

    public TaskVariableRemoveInfo() {
    }

    public TaskVariableRemoveInfo(LongIdKey taskKey, String taskVariableId) {
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
        return "TaskVariableRemoveInfo{" +
                "taskKey=" + taskKey +
                ", taskVariableId='" + taskVariableId + '\'' +
                '}';
    }
}
