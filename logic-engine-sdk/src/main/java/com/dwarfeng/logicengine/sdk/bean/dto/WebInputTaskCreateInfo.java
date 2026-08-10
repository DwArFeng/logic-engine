package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskCreateInfo implements Bean {

    private static final long serialVersionUID = -5013785659599707186L;

    public static TaskCreateInfo toStackBean(WebInputTaskCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskCreateInfo(WebInputLongIdKey.toStackBean(webInput.getSectionKey()));
        }
    }

    @JSONField(name = "section_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey sectionKey;

    public WebInputTaskCreateInfo() {
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    @Override
    public String toString() {
        return "WebInputTaskCreateInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
