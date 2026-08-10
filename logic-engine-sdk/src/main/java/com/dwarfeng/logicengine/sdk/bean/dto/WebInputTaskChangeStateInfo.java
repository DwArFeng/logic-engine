package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.logicengine.stack.bean.dto.TaskChangeStateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务状态变更信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskChangeStateInfo implements Bean {

    private static final long serialVersionUID = 8300783291686441389L;

    public static TaskChangeStateInfo toStackBean(WebInputTaskChangeStateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskChangeStateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    WebInputStateKey.toStackBean(webInput.getStateKey())
            );
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "state_key", ordinal = 2)
    @NotNull
    @Valid
    private WebInputStateKey stateKey;

    public WebInputTaskChangeStateInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public WebInputStateKey getStateKey() {
        return stateKey;
    }

    public void setStateKey(WebInputStateKey stateKey) {
        this.stateKey = stateKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskChangeStateInfo{" +
                "taskKey=" + taskKey +
                ", stateKey=" + stateKey +
                '}';
    }
}
