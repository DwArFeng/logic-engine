package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonSection implements Bean {

    private static final long serialVersionUID = -1808631084042810462L;

    public static FastJsonSection of(Section section) {
        if (Objects.isNull(section)) {
            return null;
        } else {
            return new FastJsonSection(
                    FastJsonLongIdKey.of(section.getKey()),
                    section.getName(),
                    section.isEnabled(),
                    section.getExpireTimeout(),
                    section.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "expire_timeout", ordinal = 4)
    private long expireTimeout;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonSection() {
    }

    public FastJsonSection(
            FastJsonLongIdKey key, String name, boolean enabled, long expireTimeout, String remark
    ) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.expireTimeout = expireTimeout;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonSection{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", expireTimeout=" + expireTimeout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
