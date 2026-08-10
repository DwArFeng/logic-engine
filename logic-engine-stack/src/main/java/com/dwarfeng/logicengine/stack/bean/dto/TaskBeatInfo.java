package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务心跳信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskBeatInfo implements Dto {

    private static final long serialVersionUID = 5095438625212269971L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public TaskBeatInfo() {
    }

    public TaskBeatInfo(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "TaskBeatInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
