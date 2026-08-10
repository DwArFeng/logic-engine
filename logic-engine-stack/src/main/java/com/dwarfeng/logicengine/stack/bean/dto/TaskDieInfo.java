package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务死亡信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskDieInfo implements Dto {

    private static final long serialVersionUID = -8786225376362149598L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public TaskDieInfo() {
    }

    public TaskDieInfo(LongIdKey taskKey) {
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
        return "TaskDieInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
