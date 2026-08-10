package com.dwarfeng.logicengine.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_section")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateSection implements Bean {

    private static final long serialVersionUID = -8411604460595680701L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 主属性字段

    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "expire_timeout", nullable = false)
    private long expireTimeout;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateState.class, mappedBy = "section")
    private Set<HibernateState> stateSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateDriverInfo.class, mappedBy = "section")
    private Set<HibernateDriverInfo> driverInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateGuarderInfo.class, mappedBy = "section")
    private Set<HibernateGuarderInfo> guarderInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePerformerInfo.class, mappedBy = "section")
    private Set<HibernatePerformerInfo> performerInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTask.class, mappedBy = "section")
    private Set<HibernateTask> taskSet = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "sectionDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "sectionDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateSection() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getExpireTimeout() {
        return expireTimeout;
    }

    public void setExpireTimeout(long expireTimeout) {
        this.expireTimeout = expireTimeout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<HibernateState> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<HibernateState> stateSet) {
        this.stateSet = stateSet;
    }

    public Set<HibernateDriverInfo> getDriverInfoSet() {
        return driverInfoSet;
    }

    public void setDriverInfoSet(Set<HibernateDriverInfo> driverInfoSet) {
        this.driverInfoSet = driverInfoSet;
    }

    public Set<HibernateGuarderInfo> getGuarderInfoSet() {
        return guarderInfoSet;
    }

    public void setGuarderInfoSet(Set<HibernateGuarderInfo> guarderInfoSet) {
        this.guarderInfoSet = guarderInfoSet;
    }

    public Set<HibernatePerformerInfo> getPerformerInfoSet() {
        return performerInfoSet;
    }

    public void setPerformerInfoSet(Set<HibernatePerformerInfo> performerInfoSet) {
        this.performerInfoSet = performerInfoSet;
    }

    public Set<HibernateTask> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<HibernateTask> taskSet) {
        this.taskSet = taskSet;
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
                "longId = " + longId + ", " +
                "name = " + name + ", " +
                "enabled = " + enabled + ", " +
                "expireTimeout = " + expireTimeout + ", " +
                "remark = " + remark + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
