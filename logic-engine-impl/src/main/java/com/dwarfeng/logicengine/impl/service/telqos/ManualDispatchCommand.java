package com.dwarfeng.logicengine.impl.service.telqos;

import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.logicengine.stack.service.ManualDispatchQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动调度指令。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
@TelqosCommand
public class ManualDispatchCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "manual-dispatch";

    // region 指令选项

    private static final String COMMAND_OPTION_SECTION_ID = "sid";
    private static final String COMMAND_OPTION_SECTION_ID_LONG_OPT = "section-id";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_SECTION_ID
    };

    // endregion

    private final ManualDispatchQosService manualDispatchQosService;

    public ManualDispatchCommand(ManualDispatchQosService manualDispatchQosService) {
        super(IDENTITY);
        this.manualDispatchQosService = manualDispatchQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "手动调度指定部件";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_SECTION_ID) + " section-id"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_SECTION_ID).longOpt(COMMAND_OPTION_SECTION_ID_LONG_OPT)
                .hasArg(true).type(Long.class).desc("指定待调度的部件 ID").build());
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        Long sectionId = (Long) cmd.getParsedOptionValue(COMMAND_OPTION_SECTION_ID);
        manualDispatchQosService.dispatch(new ManualDispatchInfo(new LongIdKey(sectionId)));
        context.sendMessage("部件 " + sectionId + " 已提交调度");
    }
}
