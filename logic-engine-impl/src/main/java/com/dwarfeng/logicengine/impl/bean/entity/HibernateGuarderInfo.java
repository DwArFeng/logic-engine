package com.dwarfeng.logicengine.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.logicengine.impl.bean.key.HibernateStateKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_guarder_info")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateGuarderInfo implements Bean {

    private static final long serialVersionUID = 2584770678484077087L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "section_id")
    private Long sectionLongId;

    @Column(name = "anchor_state_section_id")
    private Long anchorStateSectionLongId;

    @Column(name = "anchor_state_state_id", length = Constraints.LENGTH_STRING_ID)
    private String anchorStateStateId;

    @Column(name = "target_state_section_id")
    private Long targetStateSectionLongId;

    @Column(name = "target_state_state_id", length = Constraints.LENGTH_STRING_ID)
    private String targetStateStateId;

    // endregion

    // region 主属性字段

    @Column(name = "column_index", nullable = false)
    private int index;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "type", length = Constraints.LENGTH_TYPE)
    private String type;

    @Column(name = "param", columnDefinition = "TEXT")
    private String param;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateSection.class)
    @JoinColumns({ //
            @JoinColumn(name = "section_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateSection section;

    @ManyToOne(targetEntity = HibernateState.class)
    @JoinColumns({
            @JoinColumn(name = "anchor_state_section_id", referencedColumnName = "section_id", insertable = false, updatable = false), //
            @JoinColumn(name = "anchor_state_state_id", referencedColumnName = "state_id", insertable = false, updatable = false) //
    })
    private HibernateState anchorState;

    @ManyToOne(targetEntity = HibernateState.class)
    @JoinColumns({
            @JoinColumn(name = "target_state_section_id", referencedColumnName = "section_id", insertable = false, updatable = false), //
            @JoinColumn(name = "target_state_state_id", referencedColumnName = "state_id", insertable = false, updatable = false) //
    })
    private HibernateState targetState;

    // endregion

    // region 审计

    @DatamarkField(handlerName = "guarderDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "guarderDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateGuarderInfo() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getSectionKey() {
        return Optional.ofNullable(sectionLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setSectionKey(HibernateLongIdKey key) {
        this.sectionLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStateKey getAnchorStateKey() {
        if (java.util.Objects.isNull(anchorStateSectionLongId) || java.util.Objects.isNull(anchorStateStateId)) {
            return null;
        }
        return new HibernateStateKey(anchorStateSectionLongId, anchorStateStateId);
    }

    public void setAnchorStateKey(HibernateStateKey key) {
        if (java.util.Objects.isNull(key)) {
            this.anchorStateSectionLongId = null;
            this.anchorStateStateId = null;
        } else {
            this.anchorStateSectionLongId = key.getSectionLongId();
            this.anchorStateStateId = key.getStateId();
        }
    }

    public HibernateStateKey getTargetStateKey() {
        if (java.util.Objects.isNull(targetStateSectionLongId) || java.util.Objects.isNull(targetStateStateId)) {
            return null;
        }
        return new HibernateStateKey(targetStateSectionLongId, targetStateStateId);
    }

    public void setTargetStateKey(HibernateStateKey key) {
        if (java.util.Objects.isNull(key)) {
            this.targetStateSectionLongId = null;
            this.targetStateStateId = null;
        } else {
            this.targetStateSectionLongId = key.getSectionLongId();
            this.targetStateStateId = key.getStateId();
        }
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getSectionLongId() {
        return sectionLongId;
    }

    public void setSectionLongId(Long sectionLongId) {
        this.sectionLongId = sectionLongId;
    }

    public Long getAnchorStateSectionLongId() {
        return anchorStateSectionLongId;
    }

    public void setAnchorStateSectionLongId(Long anchorStateSectionLongId) {
        this.anchorStateSectionLongId = anchorStateSectionLongId;
    }

    public String getAnchorStateStateId() {
        return anchorStateStateId;
    }

    public void setAnchorStateStateId(String anchorStateStateId) {
        this.anchorStateStateId = anchorStateStateId;
    }

    public Long getTargetStateSectionLongId() {
        return targetStateSectionLongId;
    }

    public void setTargetStateSectionLongId(Long targetStateSectionLongId) {
        this.targetStateSectionLongId = targetStateSectionLongId;
    }

    public String getTargetStateStateId() {
        return targetStateStateId;
    }

    public void setTargetStateStateId(String targetStateStateId) {
        this.targetStateStateId = targetStateStateId;
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

    public HibernateSection getSection() {
        return section;
    }

    public void setSection(HibernateSection section) {
        this.section = section;
    }

    public HibernateState getAnchorState() {
        return anchorState;
    }

    public void setAnchorState(HibernateState anchorState) {
        this.anchorState = anchorState;
    }

    public HibernateState getTargetState() {
        return targetState;
    }

    public void setTargetState(HibernateState targetState) {
        this.targetState = targetState;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    // endregion

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "sectionLongId = " + sectionLongId + ", " +
                "anchorStateSectionLongId = " + anchorStateSectionLongId + ", " +
                "anchorStateStateId = " + anchorStateStateId + ", " +
                "targetStateSectionLongId = " + targetStateSectionLongId + ", " +
                "targetStateStateId = " + targetStateStateId + ", " +
                "index = " + index + ", " +
                "enabled = " + enabled + ", " +
                "type = " + type + ", " +
                "param = " + param + ", " +
                "remark = " + remark + ", " +
                "section = " + section + ", " +
                "anchorState = " + anchorState + ", " +
                "targetState = " + targetState + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
