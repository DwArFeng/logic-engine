package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputSection implements Bean {

    private static final long serialVersionUID = -9217286014273539678L;

    public static Section toStackBean(WebInputSection webInputSection) {
        if (Objects.isNull(webInputSection)) {
            return null;
        } else {
            return new Section(
                    WebInputLongIdKey.toStackBean(webInputSection.getKey()),
                    webInputSection.getName(),
                    webInputSection.isEnabled(),
                    webInputSection.getExpireTimeout(),
                    webInputSection.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "expire_timeout")
    @PositiveOrZero
    private long expireTimeout;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputSection() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getExpireTimeout() {
        return expireTimeout;
    }

    public void setExpireTimeout(long expireTimeout) {
        this.expireTimeout = expireTimeout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputSection{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", expireTimeout=" + expireTimeout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
