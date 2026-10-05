package com.dwarfeng.logicengine.impl.handler.guarder.compare;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarderRegistry;
import com.dwarfeng.logicengine.stack.exception.GuarderException;
import com.dwarfeng.logicengine.stack.exception.GuarderMakeException;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 比较守卫器注册。
 *
 * <p>
 * 该注册实现将参数解析为 {@link CompareGuarderConfig}，对配置进行静态校验，并为每次构造请求创建独立的比较守卫器实例。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component
public class CompareGuarderRegistry extends AbstractGuarderRegistry {

    public static final String GUARDER_TYPE = "compare_guarder";

    private final ApplicationContext ctx;

    public CompareGuarderRegistry(ApplicationContext ctx) {
        super(GUARDER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "比较守卫器";
    }

    @Override
    public String provideDescription() {
        return "按配置的比较方式比较左右操作数，成立时允许状态转移。";
    }

    @Override
    public String provideExampleParam() {
        CompareGuarderConfig config = new CompareGuarderConfig(
                CompareGuarderConstants.SOURCE_TYPE_TASK_VARIABLE, "left_variable_id", null, null,
                CompareGuarderConstants.SOURCE_TYPE_CONSTANT, null,
                CompareGuarderConstants.VALUE_TYPE_STRING, "hello", CompareGuarderConstants.COMPARATOR_EQ, false
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Guarder makeGuarder(String type, String param) throws GuarderException {
        try {
            if (Objects.isNull(param) || param.trim().isEmpty()) {
                throw new IllegalArgumentException("比较守卫器配置不能为空");
            }
            CompareGuarderConfig config = JSON.parseObject(param, CompareGuarderConfig.class);
            if (Objects.isNull(config)) {
                throw new IllegalArgumentException("比较守卫器配置不能为空");
            }
            CompareGuarderConfigValidator.validateStatic(config);
            return ctx.getBean(CompareGuarder.class, ctx, config);
        } catch (Exception e) {
            throw new GuarderMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "CompareGuarderRegistry{" +
                "ctx=" + ctx +
                ", guarderType='" + guarderType + '\'' +
                '}';
    }
}
