package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 守卫器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputGuarderInfo implements Bean {

    private static final long serialVersionUID = 3593361030209603983L;

    public static GuarderInfo toStackBean(WebInputGuarderInfo webInputGuarderInfo) {
        if (Objects.isNull(webInputGuarderInfo)) {
            return null;
        } else {
            return new GuarderInfo(
                    WebInputLongIdKey.toStackBean(webInputGuarderInfo.getKey()),
                    WebInputLongIdKey.toStackBean(webInputGuarderInfo.getSectionKey()),
                    WebInputStateKey.toStackBean(webInputGuarderInfo.getAnchorStateKey()),
                    WebInputStateKey.toStackBean(webInputGuarderInfo.getTargetStateKey()),
                    webInputGuarderInfo.getIndex(),
                    webInputGuarderInfo.isEnabled(),
                    webInputGuarderInfo.getType(),
                    webInputGuarderInfo.getParam(),
                    webInputGuarderInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "section_key")
    @Valid
    private WebInputLongIdKey sectionKey;

    @JSONField(name = "anchor_state_key")
    @Valid
    private WebInputStateKey anchorStateKey;

    @JSONField(name = "target_state_key")
    @Valid
    private WebInputStateKey targetStateKey;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "param")
    private String param;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputGuarderInfo() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public WebInputStateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(WebInputStateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public WebInputStateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(WebInputStateKey targetStateKey) {
        this.targetStateKey = targetStateKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputGuarderInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", anchorStateKey=" + anchorStateKey +
                ", targetStateKey=" + targetStateKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
