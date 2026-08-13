package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.JobCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 作业创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonJobCreateResult implements Dto {

    private static final long serialVersionUID = 126117425242418338L;

    public static FastJsonJobCreateResult of(JobCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new FastJsonJobCreateResult(FastJsonLongIdKey.of(result.getTaskKey()));
    }

    @JSONField(name = "task_key", ordinal = 1)
    private FastJsonLongIdKey taskKey;

    public FastJsonJobCreateResult() {
    }

    public FastJsonJobCreateResult(FastJsonLongIdKey taskKey) {
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
        return "FastJsonJobCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
