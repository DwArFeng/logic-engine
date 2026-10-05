package com.dwarfeng.logicengine.impl.handler.performer.cmd;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.handler.performer.AbstractPerformerRegistry;
import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.exception.PerformerMakeException;
import com.dwarfeng.logicengine.stack.handler.Performer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 命令行执行器注册。
 *
 * <p>
 * 该注册实现将参数解析为 {@link CmdPerformerConfig}，并为每次构造请求创建独立的命令行执行器实例。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component
public class CmdPerformerRegistry extends AbstractPerformerRegistry {

    public static final String PERFORMER_TYPE = "cmd_performer";

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;

    public CmdPerformerRegistry(ApplicationContext ctx, ThreadPoolTaskExecutor executor) {
        super(PERFORMER_TYPE);
        this.ctx = ctx;
        this.executor = executor;
    }

    @Override
    public String provideLabel() {
        return "命令行执行器";
    }

    @Override
    public String provideDescription() {
        return "通过操作系统的命令行启动进程，并将进程退出码回写到任务变量。";
    }

    @Override
    public String provideExampleParam() {
        CmdPerformerConfig config = new CmdPerformerConfig(
                new String[]{"cmd", "/c", "echo", "hello world"}, null, null, true, 60000L, "cmd_exit_code", -1L
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Performer makePerformer(String type, String param) throws PerformerException {
        try {
            CmdPerformerConfig config = JSON.parseObject(param, CmdPerformerConfig.class);
            if (config == null) {
                throw new IllegalArgumentException("命令行执行器配置不能为空");
            }
            return ctx.getBean(CmdPerformer.class, ctx, executor, config);
        } catch (Exception e) {
            throw new PerformerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "CmdPerformerRegistry{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", performerType='" + performerType + '\'' +
                '}';
    }
}
