package com.dwarfeng.logicengine.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 状态键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonStateKey implements Key {

    private static final long serialVersionUID = -4647559956024239780L;

    public static FastJsonStateKey of(StateKey stateKey) {
        if (Objects.isNull(stateKey)) {
            return null;
        } else {
            return new FastJsonStateKey(
                    stateKey.getSectionLongId(),
                    stateKey.getStateId()
            );
        }
    }

    @JSONField(name = "section_long_id", ordinal = 1)
    private Long sectionLongId;

    @JSONField(name = "state_id", ordinal = 2)
    private String stateId;

    public FastJsonStateKey() {
    }

    public FastJsonStateKey(Long sectionLongId, String stateId) {
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

        FastJsonStateKey that = (FastJsonStateKey) o;
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
        return "FastJsonStateKey{" +
                "sectionLongId=" + sectionLongId +
                ", stateId='" + stateId + '\'' +
                '}';
    }
}
