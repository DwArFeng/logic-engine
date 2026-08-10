package com.dwarfeng.logicengine.impl.bean.entity;

import com.dwarfeng.logicengine.impl.bean.key.HibernateStateKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_task")
public class HibernateTask implements Bean {

    private static final long serialVersionUID = 7350797530851076327L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "section_id")
    private Long sectionLongId;

    @Column(name = "current_state_section_id")
    private Long currentStateSectionLongId;

    @Column(name = "current_state_state_id", length = Constraints.LENGTH_STRING_ID)
    private String currentStateStateId;

    // endregion

    // region 主属性字段

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "started_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedDate;

    @Column(name = "ended_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "should_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldExpireDate;

    @Column(name = "should_die_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldDieDate;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    @Column(name = "died_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diedDate;

    @Column(name = "state_changed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stateChangedDate;

    @Column(name = "anchor_message", columnDefinition = "TEXT")
    private String anchorMessage;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateSection.class)
    @JoinColumns({ //
            @JoinColumn(name = "section_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateSection section;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTaskEvent.class, mappedBy = "task")
    private Set<HibernateTaskEvent> taskEventSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTaskVariable.class, mappedBy = "task")
    private Set<HibernateTaskVariable> taskVariableSet = new HashSet<>();

    // endregion

    public HibernateTask() {
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

    public HibernateStateKey getCurrentStateKey() {
        if (java.util.Objects.isNull(currentStateSectionLongId) || java.util.Objects.isNull(currentStateStateId)) {
            return null;
        }
        return new HibernateStateKey(currentStateSectionLongId, currentStateStateId);
    }

    public void setCurrentStateKey(HibernateStateKey key) {
        if (java.util.Objects.isNull(key)) {
            this.currentStateSectionLongId = null;
            this.currentStateStateId = null;
        } else {
            this.currentStateSectionLongId = key.getSectionLongId();
            this.currentStateStateId = key.getStateId();
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

    public Long getCurrentStateSectionLongId() {
        return currentStateSectionLongId;
    }

    public void setCurrentStateSectionLongId(Long currentStateSectionLongId) {
        this.currentStateSectionLongId = currentStateSectionLongId;
    }

    public String getCurrentStateStateId() {
        return currentStateStateId;
    }

    public void setCurrentStateStateId(String currentStateStateId) {
        this.currentStateStateId = currentStateStateId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDieDate() {
        return shouldDieDate;
    }

    public void setShouldDieDate(Date shouldDieDate) {
        this.shouldDieDate = shouldDieDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(Date diedDate) {
        this.diedDate = diedDate;
    }

    public Date getStateChangedDate() {
        return stateChangedDate;
    }

    public void setStateChangedDate(Date stateChangedDate) {
        this.stateChangedDate = stateChangedDate;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    public HibernateSection getSection() {
        return section;
    }

    public void setSection(HibernateSection section) {
        this.section = section;
    }

    public Set<HibernateTaskEvent> getTaskEventSet() {
        return taskEventSet;
    }

    public void setTaskEventSet(Set<HibernateTaskEvent> taskEventSet) {
        this.taskEventSet = taskEventSet;
    }

    public Set<HibernateTaskVariable> getTaskVariableSet() {
        return taskVariableSet;
    }

    public void setTaskVariableSet(Set<HibernateTaskVariable> taskVariableSet) {
        this.taskVariableSet = taskVariableSet;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "sectionLongId = " + sectionLongId + ", " +
                "currentStateSectionLongId = " + currentStateSectionLongId + ", " +
                "currentStateStateId = " + currentStateStateId + ", " +
                "status = " + status + ", " +
                "createdDate = " + createdDate + ", " +
                "startedDate = " + startedDate + ", " +
                "endedDate = " + endedDate + ", " +
                "duration = " + duration + ", " +
                "shouldExpireDate = " + shouldExpireDate + ", " +
                "shouldDieDate = " + shouldDieDate + ", " +
                "expiredDate = " + expiredDate + ", " +
                "diedDate = " + diedDate + ", " +
                "stateChangedDate = " + stateChangedDate + ", " +
                "anchorMessage = " + anchorMessage + ", " +
                "section = " + section + ")";
    }
}
