package com.dwarfeng.logicengine.impl.handler.performer.cmd;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.logicengine.sdk.handler.performer.AbstractExecutor;
import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.logicengine.stack.exception.PerformerExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 命令行执行器执行器。
 *
 * <p>
 * 该执行器通过 {@link Runtime} 启动一个外部进程，
 * 并按照 {@link CmdPerformerConfig} 的配置控制进程的工作目录、环境变量与超时时间。
 * 进程正常结束时，执行器将退出码回写到配置指定的任务变量；进程未能获得真实退出码（超时、启动失败等）时，
 * 执行器回写配置指定的哨兵退出码，并记录警告日志。
 *
 * <p>
 * 进程标准输出与标准错误的排空工作由 {@link CmdDrainTask} 提交到 {@link ThreadPoolTaskExecutor} 执行，
 * 执行器不自行创建线程；执行器在回收进程资源前等待排空任务结束。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component("cmdPerformerRegistry.cmdExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CmdExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmdExecutor.class);

    /**
     * 等待后台排空任务结束的最大时长，单位为毫秒。
     */
    private static final long DRAIN_AWAIT_TIMEOUT = 1000L;

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;

    private final CmdPerformerConfig config;

    public CmdExecutor(ApplicationContext ctx, ThreadPoolTaskExecutor executor, CmdPerformerConfig config) {
        this.ctx = ctx;
        this.executor = executor;
        this.config = config;
    }

    @Override
    public void execute() throws Exception {
        // 校验配置，配置不合法属于不可预期的配置错误，直接抛出异常。
        checkConfig();
        // 执行命令行，进程未能获得真实退出码时返回哨兵退出码。
        long exitCode = executeProcess();
        // 回写退出码。
        writeExitCode(exitCode);
    }

    /**
     * 校验配置。
     *
     * <p>
     * 该方法对命令行、超时时间进行合法性校验，对空白的工作目录与退出码变量 ID 进行归一化处理。
     *
     * @throws PerformerExecutionException 配置不合法时抛出。
     */
    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    private void checkConfig() throws PerformerExecutionException {
        // 校验命令行。
        String[] commandLine = config.getCommandLine();
        if (Objects.isNull(commandLine) || commandLine.length == 0) {
            throw new PerformerExecutionException("命令行执行器配置项 command_line 不能为空");
        }
        for (int index = 0; index < commandLine.length; index++) {
            if (Objects.isNull(commandLine[index])) {
                throw new PerformerExecutionException(
                        "命令行执行器配置项 command_line 的第 " + index + " 个元素不能为 null"
                );
            }
        }
        if (commandLine[0].trim().isEmpty()) {
            throw new PerformerExecutionException("命令行执行器配置项 command_line 的第一个元素不能为空");
        }
        // 校验超时时间。
        Long timeout = config.getTimeout();
        if (Objects.nonNull(timeout) && timeout <= 0L) {
            throw new PerformerExecutionException("命令行执行器配置项 timeout 必须为正数: " + timeout);
        }
        // 对空白的工作目录与退出码变量 ID 进行归一化处理。
        if (Objects.nonNull(config.getWorkingDirectory()) && config.getWorkingDirectory().trim().isEmpty()) {
            config.setWorkingDirectory(null);
        }
        if (Objects.nonNull(config.getExitCodeVariableId()) && config.getExitCodeVariableId().trim().isEmpty()) {
            config.setExitCodeVariableId(null);
        }
    }

    /**
     * 执行命令行。
     *
     * <p>
     * 启动失败、超时、线程中断均属于执行器可以预料到的结果，该方法不会向上抛出异常，而是返回配置的哨兵退出码。
     *
     * @return 进程的退出码；未能获得真实退出码时返回配置的哨兵退出码。
     */
    private long executeProcess() {
        File workingDirectory = makeWorkingDirectory();
        String[] environment = makeEnvironmentArray();
        long timeoutExitCode = config.getTimeoutExitCode();

        // 启动进程，启动失败属于可预期的结果，记录警告日志并返回哨兵退出码。
        Process process;
        try {
            process = Runtime.getRuntime().exec(config.getCommandLine(), environment, workingDirectory);
        } catch (IOException e) {
            LOGGER.warn("启动命令行进程失败, command_line: {}", Arrays.toString(config.getCommandLine()), e);
            return timeoutExitCode;
        }

        // 将标准输出与标准错误的排空任务提交到线程池，避免子进程因管道缓冲区写满而阻塞。
        CmdDrainTask stdoutTask = ctx.getBean(CmdDrainTask.class, process.getInputStream(), "stdout");
        CmdDrainTask stderrTask = ctx.getBean(CmdDrainTask.class, process.getErrorStream(), "stderr");
        executor.execute(stdoutTask);
        executor.execute(stderrTask);

        // 等待进程结束，超时与中断属于可预期的结果，记录警告日志并返回哨兵退出码。
        long exitCode;
        try {
            Long timeout = config.getTimeout();
            if (Objects.isNull(timeout)) {
                exitCode = process.waitFor();
            } else if (process.waitFor(timeout, TimeUnit.MILLISECONDS)) {
                exitCode = process.exitValue();
            } else {
                // 强制结束进程后回收其资源，避免遗留僵尸进程。
                // 注意：在 Windows 上 destroyForcibly 只保证终止直接子进程，其派生的孙进程未必被一并清理。
                process.destroyForcibly();
                process.waitFor();
                LOGGER.warn("命令行进程执行超时, timeout: {}ms, command_line: {}", timeout,
                        Arrays.toString(config.getCommandLine()));
                exitCode = timeoutExitCode;
            }
        } catch (InterruptedException e) {
            process.destroyForcibly();
            Thread.currentThread().interrupt();
            LOGGER.warn("命令行进程等待被中断, command_line: {}", Arrays.toString(config.getCommandLine()), e);
            exitCode = timeoutExitCode;
        }

        // 等待排空任务结束，并关闭进程的各个流。
        awaitQuietly(stdoutTask);
        awaitQuietly(stderrTask);
        closeQuietly(process.getInputStream());
        closeQuietly(process.getOutputStream());
        closeQuietly(process.getErrorStream());
        return exitCode;
    }

    /**
     * 生成进程的工作目录。
     *
     * @return 进程的工作目录；返回 <code>null</code> 表示继承当前服务进程的工作目录。
     */
    private File makeWorkingDirectory() {
        String workingDirectory = config.getWorkingDirectory();
        if (Objects.isNull(workingDirectory)) {
            return null;
        }
        return new File(workingDirectory);
    }

    /**
     * 生成进程的环境变量数组。
     *
     * <p>
     * 当 {@link CmdPerformerConfig#isInheritEnvironment()} 为 <code>true</code> 时，环境变量为当前服务进程环境
     * 与配置环境的并集，配置环境中的同名变量覆盖服务进程环境中的变量；为 <code>false</code> 时，环境变量只包含
     * 配置环境。需要注意的是，{@link Runtime#exec(String[], String[], File)} 中环境变量数组为 <code>null</code>
     * 表示继承父进程环境，因此配置环境为空且不继承服务进程环境时，必须返回长度为 0 的数组以清空环境。
     *
     * @return 进程的环境变量数组，每个元素的格式为 <code>name=value</code>。
     */
    private String[] makeEnvironmentArray() {
        // 使用大小写不敏感的容器，避免 Windows 中出现 Path 与 PATH 同时存在的重复项。
        Map<String, String> environment = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (config.isInheritEnvironment()) {
            environment.putAll(System.getenv());
        }
        Map<String, String> configEnvironment = config.getEnvironment();
        if (Objects.nonNull(configEnvironment)) {
            environment.putAll(configEnvironment);
        }
        String[] result = new String[environment.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            result[index] = entry.getKey() + "=" + entry.getValue();
            index++;
        }
        return result;
    }

    /**
     * 回写退出码。
     *
     * <p>
     * 退出码以 {@link Constants#TASK_VARIABLE_VALUE_TYPE_LONG} 类型写入配置指定的任务变量；当退出码变量 ID 为
     * <code>null</code> 时，不进行任何回写。
     *
     * @param exitCode 需要回写的退出码。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    private void writeExitCode(long exitCode) throws Exception {
        String exitCodeVariableId = config.getExitCodeVariableId();
        if (Objects.isNull(exitCodeVariableId)) {
            return;
        }
        context.upsertTaskVariable(new TaskVariableUpsertInfo(
                context.getTask().getKey(), exitCodeVariableId, Constants.TASK_VARIABLE_VALUE_TYPE_LONG, exitCode
        ));
    }

    /**
     * 在有限时长内等待排空任务结束。
     *
     * @param task 需要等待的排空任务。
     */
    private void awaitQuietly(AbstractTask task) {
        try {
            task.awaitFinish(DRAIN_AWAIT_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 静默关闭指定的可关闭对象。
     *
     * @param closeable 需要关闭的对象。
     */
    private void closeQuietly(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception ignored) {
            // 关闭流时出现的异常忽略。
        }
    }

    /**
     * 命令行进程输出排空任务。
     *
     * <p>
     * 该任务继承自 {@link AbstractTask}，作为后台任务提交到 {@link ThreadPoolTaskExecutor}，在
     * {@link AbstractTask#todo()} 中读取进程的输入流，使用 JVM 默认字符集解码输出内容，并在 debug 级别输出日志。
     * 读取行为无条件执行，避免子进程因输出缓冲区写满而阻塞。
     *
     * @author DwArFeng
     * @since 1.1.3
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class CmdDrainTask extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(CmdDrainTask.class);

        private final InputStream inputStream;
        private final String streamName;

        public CmdDrainTask(InputStream inputStream, String streamName) {
            this.inputStream = inputStream;
            this.streamName = streamName;
        }

        @Override
        protected void todo() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("命令行进程 {}: {}", streamName, line);
                    }
                }
            } catch (Exception e) {
                LOGGER.debug("读取命令行进程 {} 时出现异常", streamName, e);
            }
        }

        @Override
        public String toString() {
            return "CmdDrainTask{" +
                    "inputStream=" + inputStream +
                    ", streamName='" + streamName + '\'' +
                    ", observers=" + observers +
                    ", lock=" + lock +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CmdExecutor{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", config=" + config +
                ", context=" + context +
                '}';
    }
}
