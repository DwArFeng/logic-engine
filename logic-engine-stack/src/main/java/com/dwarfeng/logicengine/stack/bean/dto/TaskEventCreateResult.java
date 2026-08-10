package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务事件创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskEventCreateResult implements Dto {

    private static final long serialVersionUID = 2953168290338323710L;

    /**
     * 创建的任务事件主键。
     */
    private LongIdKey taskEventKey;

    public TaskEventCreateResult() {
    }

    public TaskEventCreateResult(LongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    public LongIdKey getTaskEventKey() {
        return taskEventKey;
    }

    public void setTaskEventKey(LongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    @Override
    public String toString() {
        return "TaskEventCreateResult{" +
                "taskEventKey=" + taskEventKey +
                '}';
    }
}
