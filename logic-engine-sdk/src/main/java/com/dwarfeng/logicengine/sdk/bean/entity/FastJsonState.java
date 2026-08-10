package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.FastJsonStateKey;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 状态。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonState implements Bean {

    private static final long serialVersionUID = 3505280945748671983L;

    public static FastJsonState of(State state) {
        if (Objects.isNull(state)) {
            return null;
        } else {
            return new FastJsonState(
                    FastJsonStateKey.of(state.getKey()),
                    state.getName(),
                    state.getType(),
                    state.getFirstSpinDelay(),
                    state.getSpinInterval(),
                    state.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStateKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "type", ordinal = 3)
    private int type;

    @JSONField(name = "first_spin_delay", ordinal = 4)
    private long firstSpinDelay;

    @JSONField(name = "spin_interval", ordinal = 5)
    private long spinInterval;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public FastJsonState() {
    }

    public FastJsonState(
            FastJsonStateKey key, String name, int type, long firstSpinDelay, long spinInterval, String remark
    ) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.firstSpinDelay = firstSpinDelay;
        this.spinInterval = spinInterval;
        this.remark = remark;
    }

    public FastJsonStateKey getKey() {
        return key;
    }

    public void setKey(FastJsonStateKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFirstSpinDelay() {
        return firstSpinDelay;
    }

    public void setFirstSpinDelay(long firstSpinDelay) {
        this.firstSpinDelay = firstSpinDelay;
    }

    public long getSpinInterval() {
        return spinInterval;
    }

    public void setSpinInterval(long spinInterval) {
        this.spinInterval = spinInterval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonState{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", firstSpinDelay=" + firstSpinDelay +
                ", spinInterval=" + spinInterval +
                ", remark='" + remark + '\'' +
                '}';
    }
}
