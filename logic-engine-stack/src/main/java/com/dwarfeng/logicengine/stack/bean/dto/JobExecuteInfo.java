package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 作业执行信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JobExecuteInfo implements Dto {

    private static final long serialVersionUID = 7564378050756520950L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    public JobExecuteInfo() {
    }

    public JobExecuteInfo(LongIdKey taskKey) {
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
        return "JobExecuteInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
