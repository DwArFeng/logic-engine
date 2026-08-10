package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.FastJsonStateKey;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 执行器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPerformerInfo implements Bean {

    private static final long serialVersionUID = 4053385546797955016L;

    public static FastJsonPerformerInfo of(PerformerInfo performerInfo) {
        if (Objects.isNull(performerInfo)) {
            return null;
        } else {
            return new FastJsonPerformerInfo(
                    FastJsonLongIdKey.of(performerInfo.getKey()),
                    FastJsonLongIdKey.of(performerInfo.getSectionKey()),
                    FastJsonStateKey.of(performerInfo.getAnchorStateKey()),
                    FastJsonStateKey.of(performerInfo.getTargetStateKey()),
                    performerInfo.getIndex(),
                    performerInfo.isEnabled(),
                    performerInfo.getType(),
                    performerInfo.getParam(),
                    performerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private FastJsonLongIdKey sectionKey;

    @JSONField(name = "anchor_state_key", ordinal = 3)
    private FastJsonStateKey anchorStateKey;

    @JSONField(name = "target_state_key", ordinal = 4)
    private FastJsonStateKey targetStateKey;

    @JSONField(name = "index", ordinal = 5)
    private int index;

    @JSONField(name = "enabled", ordinal = 6)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 7)
    private String type;

    @JSONField(name = "param", ordinal = 8)
    private String param;

    @JSONField(name = "remark", ordinal = 9)
    private String remark;

    public FastJsonPerformerInfo() {
    }

    public FastJsonPerformerInfo(
            FastJsonLongIdKey key, FastJsonLongIdKey sectionKey, FastJsonStateKey anchorStateKey,
            FastJsonStateKey targetStateKey, int index, boolean enabled, String type, String param, String remark
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.anchorStateKey = anchorStateKey;
        this.targetStateKey = targetStateKey;
        this.index = index;
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

    public FastJsonStateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(FastJsonStateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public FastJsonStateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(FastJsonStateKey targetStateKey) {
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
        return "FastJsonPerformerInfo{" +
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
