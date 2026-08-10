package com.dwarfeng.logicengine.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 状态键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StateKey implements Key {

    private static final long serialVersionUID = 1114383964100673534L;

    /**
     * 部件主键。
     */
    private Long sectionLongId;

    /**
     * 部件内的状态标识。
     */
    private String stateId;

    public StateKey() {
    }

    public StateKey(Long sectionLongId, String stateId) {
        this.sectionLongId = sectionLongId;
        this.stateId = stateId;
    }

    public Long getSectionLongId() {
        return sectionLongId;
    }

    public void setSectionLongId(Long sectionLongId) {
        this.sectionLongId = sectionLongId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        StateKey that = (StateKey) o;
        return Objects.equals(sectionLongId, that.sectionLongId)
                && Objects.equals(stateId, that.stateId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sectionLongId);
        result = 31 * result + Objects.hashCode(stateId);
        return result;
    }

    @Override
    public String toString() {
        return "StateKey{" +
                "sectionLongId=" + sectionLongId +
                ", stateId='" + stateId + '\'' +
                '}';
    }
}
