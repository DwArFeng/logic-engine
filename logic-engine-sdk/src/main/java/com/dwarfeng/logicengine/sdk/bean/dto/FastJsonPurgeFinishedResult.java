package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 清除完成结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPurgeFinishedResult implements Dto {

    private static final long serialVersionUID = -1830148433276222317L;

    public static FastJsonPurgeFinishedResult of(PurgeFinishedResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new FastJsonPurgeFinishedResult(result.getTaskDeletionCount(), result.isTaskDivergent());
        }
    }

    @JSONField(name = "task_deletion_count", ordinal = 1)
    private int taskDeletionCount;

    @JSONField(name = "task_divergent", ordinal = 2)
    private boolean taskDivergent;

    public FastJsonPurgeFinishedResult() {
    }

    public FastJsonPurgeFinishedResult(int taskDeletionCount, boolean taskDivergent) {
        this.taskDeletionCount = taskDeletionCount;
        this.taskDivergent = taskDivergent;
    }

    public int getTaskDeletionCount() {
        return taskDeletionCount;
    }

    public void setTaskDeletionCount(int taskDeletionCount) {
        this.taskDeletionCount = taskDeletionCount;
    }

    public boolean isTaskDivergent() {
        return taskDivergent;
    }

    public void setTaskDivergent(boolean taskDivergent) {
        this.taskDivergent = taskDivergent;
    }

    @Override
    public String toString() {
        return "FastJsonPurgeFinishedResult{" +
                "taskDeletionCount=" + taskDeletionCount +
                ", taskDivergent=" + taskDivergent +
                '}';
    }
}
