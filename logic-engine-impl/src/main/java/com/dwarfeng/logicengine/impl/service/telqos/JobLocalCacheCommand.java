package com.dwarfeng.logicengine.impl.service.telqos;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import com.dwarfeng.logicengine.stack.handler.Performer;
import com.dwarfeng.logicengine.stack.service.JobQosService;
import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@TelqosCommand
public class JobLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLocalCacheCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "jlc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final JobQosService jobQosService;

    public JobLocalCacheCommand(JobQosService jobQosService) {
        super(IDENTITY);
        this.jobQosService = jobQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "作业器本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                .desc("查询作业本地缓存").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false)
                .desc("清除作业本地缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                jobQosService.clearLocalCache();
                context.sendMessage("本地缓存已清除");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long sectionId;
        try {
            sectionId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " id");
            context.sendMessage("请留意选项 " + COMMAND_OPTION_LOOKUP + " 后接参数的类型应该是数字 ");
            return;
        }
        JobLocalCache jobLocalCache = jobQosService.getJobLocalCache(new LongIdKey(sectionId));
        if (Objects.isNull(jobLocalCache)) {
            context.sendMessage("not exists!");
            return;
        }
        printCache(context, jobLocalCache);
    }

    private void printCache(CommandExecutor.Context context, JobLocalCache jobLocalCache) throws Exception {
        context.sendMessage(String.format("section: %s", jobLocalCache.getSection()));
        context.sendMessage("");
        context.sendMessage("states:");
        for (Map.Entry<StateKey, State> entry : jobLocalCache.getStates().entrySet()) {
            context.sendMessage(String.format("  %s: %s", entry.getKey(), entry.getValue()));
        }
        context.sendMessage(String.format("initial state: %s", jobLocalCache.getInitialState()));
        context.sendMessage("terminal states:");
        for (State terminalState : jobLocalCache.getTerminalStates()) {
            context.sendMessage(String.format("  %s", terminalState));
        }
        context.sendMessage("guarders:");
        int index = 0;
        for (GuarderInfo guarderInfo : jobLocalCache.getGuarders()) {
            if (index != 0) {
                context.sendMessage("");
            }
            index++;
            Guarder guarder = jobLocalCache.getGuarderMap().get(guarderInfo.getKey());
            context.sendMessage(String.format("  %-3d %s", index, guarderInfo));
            context.sendMessage(String.format("  %-3d %s", index, guarder));
        }
        context.sendMessage("performers:");
        index = 0;
        for (PerformerInfo performerInfo : jobLocalCache.getPerformers()) {
            if (index != 0) {
                context.sendMessage("");
            }
            index++;
            Performer performer = jobLocalCache.getPerformerMap().get(performerInfo.getKey());
            context.sendMessage(String.format("  %-3d %s", index, performerInfo));
            context.sendMessage(String.format("  %-3d %s", index, performer));
        }
        context.sendMessage("warnings:");
        for (String warning : jobLocalCache.getWarnings()) {
            context.sendMessage(String.format("  %s", warning));
        }
    }
}
