package com.dwarfeng.logicengine.impl.service.telqos;

import com.dwarfeng.logicengine.stack.service.JobQosService;
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

@TelqosCommand
public class JobCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "job";

    // region 指令选项

    private static final String COMMAND_OPTION_EXECUTE = "execute";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_EXECUTE
    };

    // endregion

    private final JobQosService jobQosService;

    public JobCommand(JobQosService jobQosService) {
        super(IDENTITY);
        this.jobQosService = jobQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "作业操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_EXECUTE) + " section-id"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_EXECUTE).optionalArg(true).hasArg(true)
                .argName("section-id").type(Number.class).desc("手动执行指定部件").build());
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_EXECUTE:
                handleExecute(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleExecute(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long sectionId;
        try {
            sectionId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_EXECUTE)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage(
                    "命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                            CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_EXECUTE) + " section-id"
            );
            context.sendMessage("请留意选项 " + COMMAND_OPTION_EXECUTE + " 后接参数的类型应该是数字 ");
            return;
        }
        jobQosService.execute(new LongIdKey(sectionId));
        context.sendMessage("执行完成，可以在任务事件中查看执行结果");
    }
}
