package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 执行器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonPerformerInfo implements Bean {

    private static final long serialVersionUID = -8803847705823939751L;

    public static JSFixedFastJsonPerformerInfo of(PerformerInfo performerInfo) {
        if (Objects.isNull(performerInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonPerformerInfo(
                    JSFixedFastJsonLongIdKey.of(performerInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(performerInfo.getSectionKey()),
                    JSFixedFastJsonStateKey.of(performerInfo.getAnchorStateKey()),
                    JSFixedFastJsonStateKey.of(performerInfo.getTargetStateKey()),
                    performerInfo.getIndex(),
                    performerInfo.isEnabled(),
                    performerInfo.getType(),
                    performerInfo.getParam(),
                    performerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey sectionKey;

    @JSONField(name = "anchor_state_key", ordinal = 3)
    private JSFixedFastJsonStateKey anchorStateKey;

    @JSONField(name = "target_state_key", ordinal = 4)
    private JSFixedFastJsonStateKey targetStateKey;

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

    public JSFixedFastJsonPerformerInfo() {
    }

    public JSFixedFastJsonPerformerInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey sectionKey, JSFixedFastJsonStateKey anchorStateKey,
            JSFixedFastJsonStateKey targetStateKey, int index, boolean enabled, String type, String param, String remark
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(JSFixedFastJsonLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public JSFixedFastJsonStateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(JSFixedFastJsonStateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public JSFixedFastJsonStateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(JSFixedFastJsonStateKey targetStateKey) {
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
        return "JSFixedFastJsonPerformerInfo{" +
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
