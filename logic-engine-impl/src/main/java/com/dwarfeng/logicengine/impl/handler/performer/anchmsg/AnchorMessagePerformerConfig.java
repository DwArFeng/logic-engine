package com.dwarfeng.logicengine.impl.handler.performer.anchmsg;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 锚点消息执行器配置。
 *
 * <p>
 * 该配置用于描述一条需要写入任务锚点消息的固定文本。执行器执行时，将该文本写入任务实体的
 * <code>anchor_message</code> 字段，用于展示任务当前阶段的提示信息。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class AnchorMessagePerformerConfig implements Bean {

    private static final long serialVersionUID = -8036245916871592390L;

    @JSONField(name = "#anchor_message", ordinal = 1)
    private String anchorMessageRem = "写入任务锚点消息的固定文本。";

    @JSONField(name = "anchor_message", ordinal = 2)
    private String anchorMessage;

    public AnchorMessagePerformerConfig() {
    }

    public AnchorMessagePerformerConfig(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    public String getAnchorMessageRem() {
        return anchorMessageRem;
    }

    public void setAnchorMessageRem(String anchorMessageRem) {
        this.anchorMessageRem = anchorMessageRem;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    @Override
    public String toString() {
        return "AnchorMessagePerformerConfig{" +
                "anchorMessageRem='" + anchorMessageRem + '\'' +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
