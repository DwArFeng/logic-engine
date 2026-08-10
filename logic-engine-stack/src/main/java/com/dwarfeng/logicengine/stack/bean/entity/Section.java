package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Section implements Entity<LongIdKey> {

    private static final long serialVersionUID = -5416838890501052253L;

    private LongIdKey key;

    /**
     * 部件名称。
     */
    private String name;

    /**
     * 是否允许驱动器创建任务。
     */
    private boolean enabled;

    /**
     * 任务启动超时时间。
     */
    private long expireTimeout;

    /**
     * 备注。
     */
    private String remark;

    public Section() {
    }

    public Section(LongIdKey key, String name, boolean enabled, long expireTimeout, String remark) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.expireTimeout = expireTimeout;
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
        return "Section{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", expireTimeout=" + expireTimeout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
