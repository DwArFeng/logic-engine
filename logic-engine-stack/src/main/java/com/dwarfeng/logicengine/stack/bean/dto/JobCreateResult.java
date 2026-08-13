package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 作业创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JobCreateResult implements Dto {

    private static final long serialVersionUID = -1546754133162677760L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public JobCreateResult() {
    }

    public JobCreateResult(LongIdKey taskKey) {
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
        return "JobCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
