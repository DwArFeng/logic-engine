package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 驱动器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputDriverInfo implements Bean {

    private static final long serialVersionUID = 8055192075422472081L;

    public static DriverInfo toStackBean(WebInputDriverInfo webInputDriverInfo) {
        if (Objects.isNull(webInputDriverInfo)) {
            return null;
        } else {
            return new DriverInfo(
                    WebInputLongIdKey.toStackBean(webInputDriverInfo.getKey()),
                    WebInputLongIdKey.toStackBean(webInputDriverInfo.getSectionKey()),
                    webInputDriverInfo.isEnabled(),
                    webInputDriverInfo.getType(),
                    webInputDriverInfo.getParam(),
                    webInputDriverInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "section_key")
    @Valid
    private WebInputLongIdKey sectionKey;

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

    public WebInputDriverInfo() {
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
        return "WebInputDriverInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
