package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务过期信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskExpireInfo implements Dto {

    private static final long serialVersionUID = 7676586673483142466L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public TaskExpireInfo() {
    }

    public TaskExpireInfo(LongIdKey taskKey) {
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
        return "TaskExpireInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
