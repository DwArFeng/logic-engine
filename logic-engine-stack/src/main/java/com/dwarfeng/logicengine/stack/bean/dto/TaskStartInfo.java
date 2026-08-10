package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务启动信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskStartInfo implements Dto {

    private static final long serialVersionUID = 3454129567785074998L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public TaskStartInfo() {
    }

    public TaskStartInfo(LongIdKey taskKey) {
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
        return "TaskStartInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
