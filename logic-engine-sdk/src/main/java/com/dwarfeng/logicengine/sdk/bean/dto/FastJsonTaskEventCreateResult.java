package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 任务事件创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskEventCreateResult implements Dto {

    private static final long serialVersionUID = 2511472733877579443L;

    public static FastJsonTaskEventCreateResult of(TaskEventCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new FastJsonTaskEventCreateResult(FastJsonLongIdKey.of(result.getTaskEventKey()));
        }
    }

    @JSONField(name = "task_event_key", ordinal = 1)
    private FastJsonLongIdKey taskEventKey;

    public FastJsonTaskEventCreateResult() {
    }

    public FastJsonTaskEventCreateResult(FastJsonLongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    public FastJsonLongIdKey getTaskEventKey() {
        return taskEventKey;
    }

    public void setTaskEventKey(FastJsonLongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    @Override
    public String toString() {
        return "FastJsonTaskEventCreateResult{" +
                "taskEventKey=" + taskEventKey +
                '}';
    }
}
