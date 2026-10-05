package com.dwarfeng.logicengine.impl.handler.performer.tskevt;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 任务事件执行器配置。
 *
 * <p>
 * 该配置用于描述一条需要写入任务事件的事件消息。执行器执行时，以当前任务为主键创建任务事件，
 * 并将该文本原样写入任务事件的 <code>message</code> 字段；事件的发生时间由任务事件操作处理器统一取当前时间。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class TaskEventPerformerConfig implements Bean {

    private static final long serialVersionUID = 6641004030680965854L;

    @JSONField(name = "#message", ordinal = 1)
    private String messageRem = "写入任务事件的事件消息，原样落库。";

    @JSONField(name = "message", ordinal = 2)
    private String message;

    public TaskEventPerformerConfig() {
    }

    public TaskEventPerformerConfig(String message) {
        this.message = message;
    }

    public String getMessageRem() {
        return messageRem;
    }

    public void setMessageRem(String messageRem) {
        this.messageRem = messageRem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TaskEventPerformerConfig{" +
                "messageRem='" + messageRem + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
