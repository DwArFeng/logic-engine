package com.dwarfeng.logicengine.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.logicengine.impl.bean.key.HibernateStateKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(HibernateStateKey.class)
@Table(name = "tbl_state")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateState implements Bean {

    private static final long serialVersionUID = 7539111591622030163L;

    // region 主键

    @Id
    @Column(name = "section_id", nullable = false)
    private Long sectionLongId;

    @Id
    @Column(name = "state_id", length = Constraints.LENGTH_STRING_ID, nullable = false)
    private String stateId;

    // endregion

    // region 主属性字段

    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "first_spin_delay", nullable = false)
    private long firstSpinDelay;

    @Column(name = "spin_interval", nullable = false)
    private long spinInterval;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateSection.class)
    @JoinColumns({ //
            @JoinColumn(name = "section_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateSection section;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateGuarderInfo.class, mappedBy = "anchorState")
    private Set<HibernateGuarderInfo> anchorGuarderInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateGuarderInfo.class, mappedBy = "targetState")
    private Set<HibernateGuarderInfo> targetGuarderInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePerformerInfo.class, mappedBy = "anchorState")
    private Set<HibernatePerformerInfo> anchorPerformerInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePerformerInfo.class, mappedBy = "targetState")
    private Set<HibernatePerformerInfo> targetPerformerInfoSet = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "stateDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "stateDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateState() {
    }

    // region 映射用属性区

    public HibernateStateKey getKey() {
        if (java.util.Objects.isNull(sectionLongId) || java.util.Objects.isNull(stateId)) {
            return null;
        }
        return new HibernateStateKey(sectionLongId, stateId);
    }

    public void setKey(HibernateStateKey key) {
        if (java.util.Objects.isNull(key)) {
            this.sectionLongId = null;
            this.stateId = null;
        } else {
            this.sectionLongId = key.getSectionLongId();
            this.stateId = key.getStateId();
        }
    }

    // endregion

    // region 常规属性区

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

    public HibernateSection getSection() {
        return section;
    }

    public void setSection(HibernateSection section) {
        this.section = section;
    }

    public Set<HibernateGuarderInfo> getAnchorGuarderInfoSet() {
        return anchorGuarderInfoSet;
    }

    public void setAnchorGuarderInfoSet(Set<HibernateGuarderInfo> anchorGuarderInfoSet) {
        this.anchorGuarderInfoSet = anchorGuarderInfoSet;
    }

    public Set<HibernateGuarderInfo> getTargetGuarderInfoSet() {
        return targetGuarderInfoSet;
    }

    public void setTargetGuarderInfoSet(Set<HibernateGuarderInfo> targetGuarderInfoSet) {
        this.targetGuarderInfoSet = targetGuarderInfoSet;
    }

    public Set<HibernatePerformerInfo> getAnchorPerformerInfoSet() {
        return anchorPerformerInfoSet;
    }

    public void setAnchorPerformerInfoSet(Set<HibernatePerformerInfo> anchorPerformerInfoSet) {
        this.anchorPerformerInfoSet = anchorPerformerInfoSet;
    }

    public Set<HibernatePerformerInfo> getTargetPerformerInfoSet() {
        return targetPerformerInfoSet;
    }

    public void setTargetPerformerInfoSet(Set<HibernatePerformerInfo> targetPerformerInfoSet) {
        this.targetPerformerInfoSet = targetPerformerInfoSet;
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "sectionLongId = " + sectionLongId + ", " +
                "stateId = " + stateId + ", " +
                "name = " + name + ", " +
                "type = " + type + ", " +
                "firstSpinDelay = " + firstSpinDelay + ", " +
                "spinInterval = " + spinInterval + ", " +
                "remark = " + remark + ", " +
                "section = " + section + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
