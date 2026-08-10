package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 驱动器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DriverInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = -5784872672647576754L;

    private LongIdKey key;

    /**
     * 所属部件。
     */
    private LongIdKey sectionKey;

    /**
     * 是否启用驱动器。
     */
    private boolean enabled;

    /**
     * 驱动器类型。
     */
    private String type;

    /**
     * 驱动器参数。
     */
    private String param;

    /**
     * 备注。
     */
    private String remark;

    public DriverInfo() {
    }

    public DriverInfo(LongIdKey key, LongIdKey sectionKey, boolean enabled, String type, String param, String remark) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
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
        return "DriverInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
