package com.dwarfeng.logicengine.impl.handler.performer.groovy;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformerRegistry;
import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.exception.PerformerMakeException;
import com.dwarfeng.logicengine.stack.handler.Performer;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 执行器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class GroovyPerformerRegistry extends AbstractPerformerRegistry {

    public static final String PERFORMER_TYPE = "groovy_performer";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyPerformerRegistry.class);

    private final ApplicationContext ctx;

    public GroovyPerformerRegistry(ApplicationContext ctx) {
        super(PERFORMER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy 执行器";
    }

    @Override
    public String provideDescription() {
        return "通过 Groovy 脚本执行状态转移动作。";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExamplePerformerProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExamplePerformerProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Performer makePerformer(String type, String param) throws PerformerException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过 Groovy 脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回执行器。
            return ctx.getBean(GroovyPerformer.class, ctx, processor);
        } catch (Exception e) {
            throw new PerformerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyPerformerRegistry{" +
                "ctx=" + ctx +
                ", performerType='" + performerType + '\'' +
                '}';
    }
}
