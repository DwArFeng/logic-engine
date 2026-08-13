package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.JobCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 作业创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputJobCreateInfo implements Bean {

    private static final long serialVersionUID = -1794124644254667964L;

    public static JobCreateInfo toStackBean(WebInputJobCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new JobCreateInfo(WebInputLongIdKey.toStackBean(webInput.getSectionKey()));
    }

    @JSONField(name = "section_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey sectionKey;

    public WebInputJobCreateInfo() {
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    @Override
    public String toString() {
        return "WebInputJobCreateInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
