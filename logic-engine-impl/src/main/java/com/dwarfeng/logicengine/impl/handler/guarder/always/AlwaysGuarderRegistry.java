package com.dwarfeng.logicengine.impl.handler.guarder.always;

import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarderRegistry;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 无条件守卫器注册。
 *
 * <p>
 * 该守卫器始终返回 <code>true</code>，用于不需要附加条件的状态转移。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class AlwaysGuarderRegistry extends AbstractGuarderRegistry {

    public static final String GUARDER_TYPE = "always_guarder";

    private final ApplicationContext ctx;

    public AlwaysGuarderRegistry(ApplicationContext ctx) {
        super(GUARDER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "无条件守卫器";
    }

    @Override
    public String provideDescription() {
        return "始终允许当前状态转移。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Guarder makeGuarder(String type, String param) {
        return ctx.getBean(AlwaysGuarder.class, ctx);
    }

    @Override
    public String toString() {
        return "AlwaysGuarderRegistry{" +
                "ctx=" + ctx +
                ", guarderType='" + guarderType + '\'' +
                '}';
    }
}
