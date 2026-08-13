package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 作业执行信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputJobExecuteInfo implements Bean {

    private static final long serialVersionUID = 8948717363204193541L;

    public static JobExecuteInfo toStackBean(WebInputJobExecuteInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new JobExecuteInfo(WebInputLongIdKey.toStackBean(webInput.getTaskKey()));
    }

    @JSONField(name = "task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    public WebInputJobExecuteInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "WebInputJobExecuteInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
