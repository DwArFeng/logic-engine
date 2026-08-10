package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 驱动器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonDriverInfo implements Bean {

    private static final long serialVersionUID = -293763852463345746L;

    public static FastJsonDriverInfo of(DriverInfo driverInfo) {
        if (Objects.isNull(driverInfo)) {
            return null;
        } else {
            return new FastJsonDriverInfo(
                    FastJsonLongIdKey.of(driverInfo.getKey()),
                    FastJsonLongIdKey.of(driverInfo.getSectionKey()),
                    driverInfo.isEnabled(),
                    driverInfo.getType(),
                    driverInfo.getParam(),
                    driverInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private FastJsonLongIdKey sectionKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public FastJsonDriverInfo() {
    }

    public FastJsonDriverInfo(
            FastJsonLongIdKey key, FastJsonLongIdKey sectionKey, boolean enabled, String type, String param,
            String remark
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(FastJsonLongIdKey sectionKey) {
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
        return "FastJsonDriverInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
