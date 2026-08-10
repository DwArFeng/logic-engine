package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixedFastJson 任务事件创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskEventCreateResult implements Dto {

    private static final long serialVersionUID = 4177479293680133282L;

    public static JSFixedFastJsonTaskEventCreateResult of(TaskEventCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskEventCreateResult(JSFixedFastJsonLongIdKey.of(result.getTaskEventKey()));
        }
    }

    @JSONField(name = "task_event_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey taskEventKey;

    public JSFixedFastJsonTaskEventCreateResult() {
    }

    public JSFixedFastJsonTaskEventCreateResult(JSFixedFastJsonLongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    public JSFixedFastJsonLongIdKey getTaskEventKey() {
        return taskEventKey;
    }

    public void setTaskEventKey(JSFixedFastJsonLongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonTaskEventCreateResult{" +
                "taskEventKey=" + taskEventKey +
                '}';
    }
}
