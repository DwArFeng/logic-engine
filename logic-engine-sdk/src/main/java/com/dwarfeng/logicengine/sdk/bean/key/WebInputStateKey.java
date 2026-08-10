package com.dwarfeng.logicengine.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 状态键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputStateKey implements Key {

    private static final long serialVersionUID = -1470152026602464762L;

    public static StateKey toStackBean(WebInputStateKey webInputStateKey) {
        if (Objects.isNull(webInputStateKey)) {
            return null;
        } else {
            return new StateKey(
                    webInputStateKey.getSectionLongId(),
                    webInputStateKey.getStateId()
            );
        }
    }

    @JSONField(name = "section_long_id")
    @NotNull
    private Long sectionLongId;

    @JSONField(name = "state_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String stateId;

    public WebInputStateKey() {
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

        WebInputStateKey that = (WebInputStateKey) o;
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
        return "WebInputStateKey{" +
                "sectionLongId=" + sectionLongId +
                ", stateId='" + stateId + '\'' +
                '}';
    }
}
