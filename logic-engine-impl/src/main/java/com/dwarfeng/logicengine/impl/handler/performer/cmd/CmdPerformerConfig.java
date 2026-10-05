package com.dwarfeng.logicengine.impl.handler.performer.cmd;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Arrays;
import java.util.Map;

/**
 * 命令行执行器配置。
 *
 * <p>
 * 该配置用于描述一条需要由操作系统命令行执行的指令，以及该指令运行时所需要的工作目录、环境变量、超时时间等参数。
 * 执行器按照本配置启动进程，并在进程结束后将退出码回写到 <code>exit_code_variable_id</code> 指定的任务变量中。
 * 当进程未能获得真实退出码（超时、启动失败等）时，执行器回写 <code>timeout_exit_code</code> 指定的哨兵退出码。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
public class CmdPerformerConfig implements Bean {

    private static final long serialVersionUID = -843487995025042988L;

    @JSONField(name = "#command_line", ordinal = 1)
    private String commandLineRem = "命令行数组，第一个元素为可执行文件，其后元素为参数。";

    @JSONField(name = "command_line", ordinal = 2)
    private String[] commandLine;

    @JSONField(name = "#working_directory", ordinal = 3)
    private String workingDirectoryRem = "进程工作目录，为空则表示继承当前服务进程的工作目录，允许使用相对路径。";

    @JSONField(name = "working_directory", ordinal = 4)
    private String workingDirectory;

    @JSONField(name = "#environment", ordinal = 5)
    private String environmentRem = "环境变量映射，键为变量名，值为变量值。";

    @JSONField(name = "environment", ordinal = 6)
    private Map<String, String> environment;

    @JSONField(name = "#inherit_environment", ordinal = 7)
    private String inheritEnvironmentRem = "是否继承当前服务进程的环境变量，为 true 时环境变量为服务进程环境与配置环境的并集。";

    @JSONField(name = "inherit_environment", ordinal = 8)
    private boolean inheritEnvironment = true;

    @JSONField(name = "#timeout", ordinal = 9)
    private String timeoutRem = "进程执行的超时时间，单位为毫秒，为空则表示不限制超时时间。";

    @JSONField(name = "timeout", ordinal = 10)
    private Long timeout;

    @JSONField(name = "#exit_code_variable_id", ordinal = 11)
    private String exitCodeVariableIdRem = "退出码回写的任务变量 ID，为空则表示不回写退出码。";

    @JSONField(name = "exit_code_variable_id", ordinal = 12)
    private String exitCodeVariableId;

    @JSONField(name = "#timeout_exit_code", ordinal = 13)
    private String timeoutExitCodeRem = "未能获得真实退出码（超时、启动失败等）时回写的哨兵退出码。";

    @JSONField(name = "timeout_exit_code", ordinal = 14)
    private long timeoutExitCode = -1L;

    public CmdPerformerConfig() {
    }

    public CmdPerformerConfig(
            String[] commandLine, String workingDirectory, Map<String, String> environment, boolean inheritEnvironment,
            Long timeout, String exitCodeVariableId, long timeoutExitCode
    ) {
        this.commandLine = commandLine;
        this.workingDirectory = workingDirectory;
        this.environment = environment;
        this.inheritEnvironment = inheritEnvironment;
        this.timeout = timeout;
        this.exitCodeVariableId = exitCodeVariableId;
        this.timeoutExitCode = timeoutExitCode;
    }

    public String getCommandLineRem() {
        return commandLineRem;
    }

    public void setCommandLineRem(String commandLineRem) {
        this.commandLineRem = commandLineRem;
    }

    public String[] getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String[] commandLine) {
        this.commandLine = commandLine;
    }

    public String getWorkingDirectoryRem() {
        return workingDirectoryRem;
    }

    public void setWorkingDirectoryRem(String workingDirectoryRem) {
        this.workingDirectoryRem = workingDirectoryRem;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String getEnvironmentRem() {
        return environmentRem;
    }

    public void setEnvironmentRem(String environmentRem) {
        this.environmentRem = environmentRem;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public String getInheritEnvironmentRem() {
        return inheritEnvironmentRem;
    }

    public void setInheritEnvironmentRem(String inheritEnvironmentRem) {
        this.inheritEnvironmentRem = inheritEnvironmentRem;
    }

    public boolean isInheritEnvironment() {
        return inheritEnvironment;
    }

    public void setInheritEnvironment(boolean inheritEnvironment) {
        this.inheritEnvironment = inheritEnvironment;
    }

    public String getTimeoutRem() {
        return timeoutRem;
    }

    public void setTimeoutRem(String timeoutRem) {
        this.timeoutRem = timeoutRem;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getExitCodeVariableIdRem() {
        return exitCodeVariableIdRem;
    }

    public void setExitCodeVariableIdRem(String exitCodeVariableIdRem) {
        this.exitCodeVariableIdRem = exitCodeVariableIdRem;
    }

    public String getExitCodeVariableId() {
        return exitCodeVariableId;
    }

    public void setExitCodeVariableId(String exitCodeVariableId) {
        this.exitCodeVariableId = exitCodeVariableId;
    }

    public String getTimeoutExitCodeRem() {
        return timeoutExitCodeRem;
    }

    public void setTimeoutExitCodeRem(String timeoutExitCodeRem) {
        this.timeoutExitCodeRem = timeoutExitCodeRem;
    }

    public long getTimeoutExitCode() {
        return timeoutExitCode;
    }

    public void setTimeoutExitCode(long timeoutExitCode) {
        this.timeoutExitCode = timeoutExitCode;
    }

    @Override
    public String toString() {
        return "CmdPerformerConfig{" +
                "commandLineRem='" + commandLineRem + '\'' +
                ", commandLine=" + Arrays.toString(commandLine) +
                ", workingDirectoryRem='" + workingDirectoryRem + '\'' +
                ", workingDirectory='" + workingDirectory + '\'' +
                ", environmentRem='" + environmentRem + '\'' +
                ", environment=" + environment +
                ", inheritEnvironmentRem='" + inheritEnvironmentRem + '\'' +
                ", inheritEnvironment=" + inheritEnvironment +
                ", timeoutRem='" + timeoutRem + '\'' +
                ", timeout=" + timeout +
                ", exitCodeVariableIdRem='" + exitCodeVariableIdRem + '\'' +
                ", exitCodeVariableId='" + exitCodeVariableId + '\'' +
                ", timeoutExitCodeRem='" + timeoutExitCodeRem + '\'' +
                ", timeoutExitCode=" + timeoutExitCode +
                '}';
    }
}
