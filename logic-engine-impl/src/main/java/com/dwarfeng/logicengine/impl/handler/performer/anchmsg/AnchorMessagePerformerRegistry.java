package com.dwarfeng.logicengine.impl.handler.performer.anchmsg;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformerRegistry;
import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.exception.PerformerMakeException;
import com.dwarfeng.logicengine.stack.handler.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 锚点消息执行器注册。
 *
 * <p>
 * 该注册实现将参数解析为 {@link AnchorMessagePerformerConfig}，对配置进行静态校验，并为每次构造请求创建独立的
 * 锚点消息执行器实例。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Component
public class AnchorMessagePerformerRegistry extends AbstractPerformerRegistry {

    public static final String PERFORMER_TYPE = "anchor_message_performer";

    private final ApplicationContext ctx;

    public AnchorMessagePerformerRegistry(ApplicationContext ctx) {
        super(PERFORMER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "锚点消息执行器";
    }

    @Override
    public String provideDescription() {
        return "将配置中的锚点消息写入任务。";
    }

    @Override
    public String provideExampleParam() {
        AnchorMessagePerformerConfig config = new AnchorMessagePerformerConfig("任务开始执行。");
        return JSON.toJSONString(config, true);
    }

    @Override
    public Performer makePerformer(String type, String param) throws PerformerException {
        try {
            if (Objects.isNull(param) || param.trim().isEmpty()) {
                throw new IllegalArgumentException("锚点消息执行器配置不能为空");
            }
            AnchorMessagePerformerConfig config = JSON.parseObject(param, AnchorMessagePerformerConfig.class);
            if (Objects.isNull(config)) {
                throw new IllegalArgumentException("锚点消息执行器配置不能为空");
            }
            if (Objects.isNull(config.getAnchorMessage()) || config.getAnchorMessage().trim().isEmpty()) {
                throw new IllegalArgumentException("锚点消息执行器配置项 anchor_message 不能为空");
            }
            return ctx.getBean(AnchorMessagePerformer.class, ctx, config);
        } catch (Exception e) {
            throw new PerformerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "AnchorMessagePerformerRegistry{" +
                "ctx=" + ctx +
                ", performerType='" + performerType + '\'' +
                '}';
    }
}
