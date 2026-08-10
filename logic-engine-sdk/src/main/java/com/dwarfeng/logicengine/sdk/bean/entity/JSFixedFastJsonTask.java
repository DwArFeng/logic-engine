package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTask implements Bean {

    private static final long serialVersionUID = -5675198367404189850L;

    public static JSFixedFastJsonTask of(Task task) {
        if (Objects.isNull(task)) {
            return null;
        } else {
            return new JSFixedFastJsonTask(
                    JSFixedFastJsonLongIdKey.of(task.getKey()),
                    JSFixedFastJsonLongIdKey.of(task.getSectionKey()),
                    JSFixedFastJsonStateKey.of(task.getCurrentStateKey()),
                    task.getStatus(),
                    task.getCreatedDate(),
                    task.getStartedDate(),
                    task.getEndedDate(),
                    task.getDuration(),
                    task.getShouldExpireDate(),
                    task.getShouldDieDate(),
                    task.getExpiredDate(),
                    task.getDiedDate(),
                    task.getStateChangedDate(),
                    task.getAnchorMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey sectionKey;

    @JSONField(name = "current_state_key", ordinal = 3)
    private JSFixedFastJsonStateKey currentStateKey;

    @JSONField(name = "status", ordinal = 4)
    private int status;

    @JSONField(name = "created_date", ordinal = 5)
    private Date createdDate;

    @JSONField(name = "started_date", ordinal = 6)
    private Date startedDate;

    @JSONField(name = "ended_date", ordinal = 7)
    private Date endedDate;

    @JSONField(name = "duration", ordinal = 8, serializeUsing = ToStringSerializer.class)
    private Long duration;

    @JSONField(name = "should_expire_date", ordinal = 9)
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date", ordinal = 10)
    private Date shouldDieDate;

    @JSONField(name = "expired_date", ordinal = 11)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 12)
    private Date diedDate;

    @JSONField(name = "state_changed_date", ordinal = 13)
    private Date stateChangedDate;

    @JSONField(name = "anchor_message", ordinal = 14)
    private String anchorMessage;

    public JSFixedFastJsonTask() {
    }

    public JSFixedFastJsonTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey sectionKey, JSFixedFastJsonStateKey currentStateKey,
            int status, Date createdDate, Date startedDate, Date endedDate, Long duration, Date shouldExpireDate,
            Date shouldDieDate, Date expiredDate, Date diedDate, Date stateChangedDate, String anchorMessage
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(JSFixedFastJsonLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public JSFixedFastJsonStateKey getCurrentStateKey() {
        return currentStateKey;
    }

    public void setCurrentStateKey(JSFixedFastJsonStateKey currentStateKey) {
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
        return "JSFixedFastJsonTask{" +
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
