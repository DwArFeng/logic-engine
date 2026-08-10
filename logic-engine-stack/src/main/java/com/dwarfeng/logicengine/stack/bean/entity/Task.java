package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Task implements Entity<LongIdKey> {

    private static final long serialVersionUID = -298530525963775451L;

    private LongIdKey key;
    /**
     * 所属部件。
     */
    private LongIdKey sectionKey;

    /**
     * 当前状态；任务处于 CREATED 且尚无有效执行快照时可为空。
     */
    private StateKey currentStateKey;

    /**
     * 任务生命周期状态。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>任务创建</li>
     *     <li>任务进行</li>
     *     <li>任务完成</li>
     *     <li>任务失败</li>
     *     <li>任务过期</li>
     *     <li>任务死亡</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int status;

    /**
     * 任务创建时间。
     */
    private Date createdDate;

    /**
     * 任务开始执行时间。
     */
    private Date startedDate;

    /**
     * 任务结束时间。
     */
    private Date endedDate;

    /**
     * 任务执行持续时间，单位为毫秒。
     */
    private Long duration;

    /**
     * 任务开始前不可续期的绝对过期截止时间。
     */
    private Date shouldExpireDate;

    /**
     * 任务执行期间可由心跳续期的死亡截止时间。
     */
    private Date shouldDieDate;

    /**
     * 任务进入 EXPIRED 状态的时间。
     */
    private Date expiredDate;

    /**
     * 任务进入 DIED 状态的时间。
     */
    private Date diedDate;

    /**
     * 最近一次进入当前状态的时间。
     */
    private Date stateChangedDate;

    /**
     * 任务生命周期或状态处理摘要。
     */
    private String anchorMessage;

    public Task() {
    }

    public Task(
            LongIdKey key, LongIdKey sectionKey, StateKey currentStateKey, int status, Date createdDate,
            Date startedDate, Date endedDate, Long duration, Date shouldExpireDate, Date shouldDieDate,
            Date expiredDate, Date diedDate, Date stateChangedDate, String anchorMessage
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.currentStateKey = currentStateKey;
        this.status = status;
        this.createdDate = createdDate;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.duration = duration;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDieDate = shouldDieDate;
        this.expiredDate = expiredDate;
        this.diedDate = diedDate;
        this.stateChangedDate = stateChangedDate;
        this.anchorMessage = anchorMessage;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public StateKey getCurrentStateKey() {
        return currentStateKey;
    }

    public void setCurrentStateKey(StateKey currentStateKey) {
        this.currentStateKey = currentStateKey;
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

    @Override
    public String toString() {
        return "Task{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", currentStateKey=" + currentStateKey +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", duration=" + duration +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDieDate=" + shouldDieDate +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", stateChangedDate=" + stateChangedDate +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
