package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 任务创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskCreateResult implements Dto {

    private static final long serialVersionUID = -3928469096995732414L;

    public static FastJsonTaskCreateResult of(TaskCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new FastJsonTaskCreateResult(FastJsonLongIdKey.of(result.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    private FastJsonLongIdKey taskKey;

    public FastJsonTaskCreateResult() {
    }

    public FastJsonTaskCreateResult(FastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public FastJsonLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(FastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "FastJsonTaskCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
