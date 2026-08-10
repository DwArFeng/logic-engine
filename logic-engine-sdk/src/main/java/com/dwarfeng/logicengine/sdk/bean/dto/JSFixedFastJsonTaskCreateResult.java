package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixedFastJson 任务创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskCreateResult implements Dto {

    private static final long serialVersionUID = -5463464080866192543L;

    public static JSFixedFastJsonTaskCreateResult of(TaskCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskCreateResult(JSFixedFastJsonLongIdKey.of(result.getTaskKey()));
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey taskKey;

    public JSFixedFastJsonTaskCreateResult() {
    }

    public JSFixedFastJsonTaskCreateResult(JSFixedFastJsonLongIdKey taskKey) {
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
        return "JSFixedFastJsonTaskCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
