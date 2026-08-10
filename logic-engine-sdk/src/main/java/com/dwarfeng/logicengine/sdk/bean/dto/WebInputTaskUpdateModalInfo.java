package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskUpdateModalInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务更新模态信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskUpdateModalInfo implements Bean {

    private static final long serialVersionUID = 6528583051830201123L;

    public static TaskUpdateModalInfo toStackBean(WebInputTaskUpdateModalInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskUpdateModalInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()), webInput.getAnchorMessage()
            );
        }
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "anchor_message", ordinal = 2)
    private String anchorMessage;

    public WebInputTaskUpdateModalInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    @Override
    public String toString() {
        return "WebInputTaskUpdateModalInfo{" +
                "taskKey=" + taskKey +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
