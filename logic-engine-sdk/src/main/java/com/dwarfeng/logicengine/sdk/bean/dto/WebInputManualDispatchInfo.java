package com.dwarfeng.logicengine.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 手动调度信息。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
public class WebInputManualDispatchInfo implements Bean {

    private static final long serialVersionUID = 6366561559909832389L;

    public static ManualDispatchInfo toStackBean(WebInputManualDispatchInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new ManualDispatchInfo(WebInputLongIdKey.toStackBean(webInput.getSectionKey()));
    }

    @JSONField(name = "section_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey sectionKey;

    public WebInputManualDispatchInfo() {
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    @Override
    public String toString() {
        return "WebInputManualDispatchInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
