package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.logicengine.sdk.util.ValidTaskStatus;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTask implements Bean {

    private static final long serialVersionUID = -8168410249590579359L;

    public static Task toStackBean(WebInputTask webInputTask) {
        if (Objects.isNull(webInputTask)) {
            return null;
        } else {
            return new Task(
                    WebInputLongIdKey.toStackBean(webInputTask.getKey()),
                    WebInputLongIdKey.toStackBean(webInputTask.getSectionKey()),
                    WebInputStateKey.toStackBean(webInputTask.getCurrentStateKey()),
                    webInputTask.getStatus(),
                    webInputTask.getCreatedDate(),
                    webInputTask.getStartedDate(),
                    webInputTask.getEndedDate(),
                    webInputTask.getDuration(),
                    webInputTask.getShouldExpireDate(),
                    webInputTask.getShouldDieDate(),
                    webInputTask.getExpiredDate(),
                    webInputTask.getDiedDate(),
                    webInputTask.getStateChangedDate(),
                    webInputTask.getAnchorMessage()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "section_key")
    @Valid
    private WebInputLongIdKey sectionKey;

    @JSONField(name = "current_state_key")
    @Valid
    private WebInputStateKey currentStateKey;

    @JSONField(name = "status")
    @ValidTaskStatus
    private int status;

    @JSONField(name = "created_date")
    private Date createdDate;

    @JSONField(name = "started_date")
    private Date startedDate;

    @JSONField(name = "ended_date")
    private Date endedDate;

    @JSONField(name = "duration")
    @PositiveOrZero
    private Long duration;

    @JSONField(name = "should_expire_date")
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date")
    private Date shouldDieDate;

    @JSONField(name = "expired_date")
    private Date expiredDate;

    @JSONField(name = "died_date")
    private Date diedDate;

    @JSONField(name = "state_changed_date")
    private Date stateChangedDate;

    @JSONField(name = "anchor_message")
    private String anchorMessage;

    public WebInputTask() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public WebInputStateKey getCurrentStateKey() {
        return currentStateKey;
    }

    public void setCurrentStateKey(WebInputStateKey currentStateKey) {
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
        return "WebInputTask{" +
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
