package com.dwarfeng.logicengine.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateStateKey implements Key {

    private static final long serialVersionUID = -4912063225293818847L;

    private Long sectionLongId;
    private String stateId;

    public HibernateStateKey() {
    }

    public HibernateStateKey(Long sectionLongId, String stateId) {
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

        HibernateStateKey that = (HibernateStateKey) o;
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
        return "HibernateStateKey{" +
                "sectionLongId=" + sectionLongId +
                ", stateId='" + stateId + '\'' +
                '}';
    }
}
