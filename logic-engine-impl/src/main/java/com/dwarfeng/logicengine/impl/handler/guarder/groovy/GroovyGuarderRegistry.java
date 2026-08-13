package com.dwarfeng.logicengine.impl.handler.guarder.groovy;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.logicengine.sdk.handler.guarder.AbstractGuarderRegistry;
import com.dwarfeng.logicengine.stack.exception.GuarderException;
import com.dwarfeng.logicengine.stack.exception.GuarderMakeException;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 守卫器注册。
 *
 * <p>
 * 该注册实现将参数作为 Groovy 源码编译为 {@link Processor}，并为每次构造请求创建独立的守卫器实例。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class GroovyGuarderRegistry extends AbstractGuarderRegistry {

    public static final String GUARDER_TYPE = "groovy_guarder";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyGuarderRegistry.class);

    private final ApplicationContext ctx;

    public GroovyGuarderRegistry(ApplicationContext ctx) {
        super(GUARDER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy 守卫器";
    }

    @Override
    public String provideDescription() {
        return "通过 Groovy 脚本判断当前状态是否可以转移。";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleGuarderProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleGuarderProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Guarder makeGuarder(String type, String param) throws GuarderException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过 Groovy 脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回守卫器。
            return ctx.getBean(GroovyGuarder.class, ctx, processor);
        } catch (Exception e) {
            throw new GuarderMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyGuarderRegistry{" +
                "ctx=" + ctx +
                ", guarderType='" + guarderType + '\'' +
                '}';
    }
}
