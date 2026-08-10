package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 守卫器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GuarderInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = -4653281959857337571L;

    private LongIdKey key;

    /**
     * 所属部件。
     */
    private LongIdKey sectionKey;

    /**
     * 条件锚点状态。
     */
    private StateKey anchorStateKey;

    /**
     * 条件目标状态。
     */
    private StateKey targetStateKey;

    /**
     * 同一锚点状态下的排序索引。索引相同时按主键升序排列。
     */
    private int index;

    /**
     * 是否参与状态图校验及运行时条件判断。
     */
    private boolean enabled;

    /**
     * 守卫器类型。
     */
    private String type;

    /**
     * 守卫器参数。
     */
    private String param;

    /**
     * 备注。
     */
    private String remark;

    public GuarderInfo() {
    }

    public GuarderInfo(
            LongIdKey key, LongIdKey sectionKey, StateKey anchorStateKey, StateKey targetStateKey, int index,
            boolean enabled, String type, String param, String remark
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

    public StateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(StateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public StateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(StateKey targetStateKey) {
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
        return "GuarderInfo{" +
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
