package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.JobCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixedFastJson 作业创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonJobCreateResult implements Dto {

    private static final long serialVersionUID = -8010340098310899573L;

    public static JSFixedFastJsonJobCreateResult of(JobCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new JSFixedFastJsonJobCreateResult(JSFixedFastJsonLongIdKey.of(result.getTaskKey()));
    }

    @JSONField(name = "task_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey taskKey;

    public JSFixedFastJsonJobCreateResult() {
    }

    public JSFixedFastJsonJobCreateResult(JSFixedFastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public JSFixedFastJsonLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(JSFixedFastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonJobCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
