package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 状态。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class State implements Entity<StateKey> {

    private static final long serialVersionUID = -8270717946093864350L;

    private StateKey key;

    /**
     * 状态名称。
     */
    private String name;

    /**
     * 状态类型。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>初始状态</li>
     *     <li>普通状态</li>
     *     <li>结束状态</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int type;

    /**
     * 首次 spin 前的冷却时间，单位为毫秒；0 表示立即执行。
     */
    private long firstSpinDelay;

    /**
     * 未发生状态转移时的 spin 间隔，单位为毫秒。
     */
    private long spinInterval;

    /**
     * 备注。
     */
    private String remark;

    public State() {
    }

    public State(StateKey key, String name, int type, long firstSpinDelay, long spinInterval, String remark) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.firstSpinDelay = firstSpinDelay;
        this.spinInterval = spinInterval;
        this.remark = remark;
    }

    @Override
    public StateKey getKey() {
        return key;
    }

    @Override
    public void setKey(StateKey key) {
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
        return "State{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", firstSpinDelay=" + firstSpinDelay +
                ", spinInterval=" + spinInterval +
                ", remark='" + remark + '\'' +
                '}';
    }
}
