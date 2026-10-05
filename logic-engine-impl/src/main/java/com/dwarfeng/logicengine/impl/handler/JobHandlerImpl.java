package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.*;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.handler.*;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class JobHandlerImpl implements JobHandler {

    private final ApplicationContext ctx;

    private final TaskMaintainService taskMaintainService;

    private final JobLocalCacheHandler jobLocalCacheHandler;
    private final TaskOperateHandler taskOperateHandler;
    private final TaskEventOperateHandler taskEventOperateHandler;
    private final TaskVariableOperateHandler taskVariableOperateHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.logicengine.task.beat_interval}")
    private long beatInterval;

    private final ConcurrentMap<LongIdKey, Lock> executeLocks = new ConcurrentHashMap<>();

    public JobHandlerImpl(
            ApplicationContext ctx,
            TaskMaintainService taskMaintainService,
            JobLocalCacheHandler jobLocalCacheHandler,
            TaskOperateHandler taskOperateHandler,
            TaskEventOperateHandler taskEventOperateHandler,
            TaskVariableOperateHandler taskVariableOperateHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.ctx = ctx;
        this.taskMaintainService = taskMaintainService;
        this.jobLocalCacheHandler = jobLocalCacheHandler;
        this.taskOperateHandler = taskOperateHandler;
        this.taskEventOperateHandler = taskEventOperateHandler;
        this.taskVariableOperateHandler = taskVariableOperateHandler;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Override
    public JobCreateResult create(JobCreateInfo info) throws HandlerException {
        Objects.requireNonNull(info, "作业创建信息不能为 null");
        TaskCreateResult result = taskOperateHandler.create(new TaskCreateInfo(info.getSectionKey()));
        return new JobCreateResult(result.getTaskKey());
    }

    @Override
    public void execute(JobExecuteInfo info) throws HandlerException {
        Objects.requireNonNull(info, "作业执行信息不能为 null");
        LongIdKey taskKey = info.getTaskKey();

        JobLocalCache cache;
        Lock executeLock = executeLocks.computeIfAbsent(taskKey, ignored -> new ReentrantLock());
        executeLock.lock();
        try {
            Task task = getTask(taskKey);
            if (task.getStatus() != Constants.TASK_STATUS_CREATED) {
                return;
            }
            if (isExpired(task)) {
                taskOperateHandler.expire(new TaskExpireInfo(taskKey));
                createEvent(taskKey, "任务在启动前超过最终截止时间。");
                return;
            }
            try {
                cache = jobLocalCacheHandler.get(task.getSectionKey());
            } catch (Exception e) {
                failIfActive(taskKey, "执行快照加载失败: " + messageOf(e));
                return;
            }
            taskOperateHandler.start(new TaskStartInfo(taskKey));
            taskOperateHandler.changeState(new TaskChangeStateInfo(taskKey, cache.getInitialState().getKey()));
        } finally {
            executeLock.unlock();
        }

        createEvent(taskKey, "任务已开始执行。");
        for (String warning : cache.getWarnings()) {
            createEvent(taskKey, "执行快照警告: " + warning);
        }

        ScheduledFuture<?> heartbeatFuture = scheduleHeartbeat(taskKey);
        try {
            runStateMachine(taskKey, cache);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failIfActive(taskKey, "任务执行被中断。");
        } catch (Exception e) {
            failIfActive(taskKey, "任务执行失败: " + messageOf(e));
        } finally {
            heartbeatFuture.cancel(false);
        }
    }

    @Override
    public CompletableFuture<Void> executeAsync(JobExecuteInfo info) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        executor.execute(() -> {
            try {
                execute(info);
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    private void runStateMachine(LongIdKey taskKey, JobLocalCache cache) throws Exception {
        State currentState = cache.getInitialState();
        while (continueProcessing(taskKey)) {
            if (currentState.getType() == Constants.STATE_TYPE_TERMINAL) {
                taskOperateHandler.finish(new TaskFinishInfo(taskKey));
                createEvent(taskKey, "任务到达结束状态: " + currentState.getKey().getStateId());
                return;
            }

            sleep(currentState.getFirstSpinDelay());
            while (continueProcessing(taskKey)) {
                Guarder.Context guarderContext = ctx.getBean(
                        GuarderContext.class, getTask(taskKey), cache, currentState, taskVariableOperateHandler,
                        taskEventOperateHandler, taskOperateHandler
                );
                GuarderInfo selectedGuarder = selectGuarder(cache, guarderContext, currentState);
                if (selectedGuarder == null) {
                    sleep(currentState.getSpinInterval());
                    continue;
                }
                if (!continueProcessing(taskKey)) {
                    return;
                }

                State targetState = cache.getStates().get(selectedGuarder.getTargetStateKey());
                Performer.Context performerContext = ctx.getBean(
                        PerformerContext.class, getTask(taskKey), cache, currentState, targetState,
                        taskVariableOperateHandler
                );
                executePerformers(cache, performerContext, selectedGuarder);
                if (!continueProcessing(taskKey)) {
                    return;
                }
                taskOperateHandler.changeState(new TaskChangeStateInfo(taskKey, targetState.getKey()));
                createEvent(
                        taskKey,
                        "状态转移: " + currentState.getKey().getStateId() + " -> " +
                                targetState.getKey().getStateId()
                );
                currentState = targetState;
                break;
            }
        }
    }

    @Nullable
    private GuarderInfo selectGuarder(JobLocalCache cache, Guarder.Context context, State currentState)
            throws Exception {
        for (GuarderInfo guarderInfo : cache.getGuarders()) {
            if (!guarderInfo.isEnabled()
                    || !Objects.equals(currentState.getKey(), guarderInfo.getAnchorStateKey())) {
                continue;
            }
            Guarder guarder = cache.getGuarderMap().get(guarderInfo.getKey());
            Guarder.Executor executor = guarder.newExecutor();
            executor.init(context);
            if (executor.test()) {
                return guarderInfo;
            }
        }
        return null;
    }

    private void executePerformers(JobLocalCache cache, Performer.Context context, GuarderInfo selectedGuarder)
            throws Exception {
        for (PerformerInfo performerInfo : cache.getPerformers()) {
            if (!performerInfo.isEnabled()
                    || !Objects.equals(selectedGuarder.getAnchorStateKey(), performerInfo.getAnchorStateKey())
                    || !Objects.equals(selectedGuarder.getTargetStateKey(), performerInfo.getTargetStateKey())) {
                continue;
            }
            Performer performer = cache.getPerformerMap().get(performerInfo.getKey());
            Performer.Executor executor = performer.newExecutor();
            executor.init(context);
            executor.execute();
        }
    }

    private ScheduledFuture<?> scheduleHeartbeat(LongIdKey taskKey) {
        return scheduler.scheduleAtFixedRate(() -> {
            try {
                if (isProcessing(taskKey)) {
                    taskOperateHandler.beat(new TaskBeatInfo(taskKey));
                }
            } catch (Exception ignored) {
                // 本次心跳更新失败，下一周期继续尝试。
            }
        }, beatInterval);
    }

    private boolean continueProcessing(LongIdKey taskKey) throws HandlerException {
        Task task = getTask(taskKey);
        if (task.getStatus() != Constants.TASK_STATUS_PROCESSING) {
            return false;
        }
        if (isExpired(task)) {
            taskOperateHandler.expire(new TaskExpireInfo(taskKey));
            createEvent(taskKey, "任务执行超过最终截止时间。");
            return false;
        }
        return true;
    }

    private boolean isProcessing(LongIdKey taskKey) throws HandlerException {
        return getTask(taskKey).getStatus() == Constants.TASK_STATUS_PROCESSING;
    }

    private void failIfActive(LongIdKey taskKey, String message) throws HandlerException {
        Task task = getTask(taskKey);
        if (task.getStatus() != Constants.TASK_STATUS_CREATED
                && task.getStatus() != Constants.TASK_STATUS_PROCESSING) {
            return;
        }
        taskOperateHandler.fail(new TaskFailInfo(taskKey));
        createEvent(taskKey, message);
    }

    private Task getTask(LongIdKey taskKey) throws HandlerException {
        try {
            if (taskKey == null || !taskMaintainService.exists(taskKey)) {
                throw new IllegalArgumentException("任务不存在: " + taskKey);
            }
            return taskMaintainService.get(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void createEvent(LongIdKey taskKey, String message) throws HandlerException {
        taskEventOperateHandler.create(new TaskEventCreateInfo(taskKey, new Date(), message));
    }

    private static boolean isExpired(Task task) {
        return task.getShouldExpireDate() != null && !task.getShouldExpireDate().after(new Date());
    }

    private static void sleep(long millis) throws InterruptedException {
        if (millis > 0) {
            Thread.sleep(millis);
        } else {
            Thread.yield();
        }
    }

    private static String messageOf(Exception e) {
        String message = e.getMessage();
        return message == null || message.trim().isEmpty() ? e.getClass().getSimpleName() : message;
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GuarderContext implements Guarder.Context {

        private final Task task;
        private final JobLocalCache cache;
        private final State currentState;
        private final TaskVariableOperateHandler taskVariableOperateHandler;
        private final TaskEventOperateHandler taskEventOperateHandler;
        private final TaskOperateHandler taskOperateHandler;

        public GuarderContext(
                Task task,
                JobLocalCache cache,
                State currentState,
                TaskVariableOperateHandler taskVariableOperateHandler,
                TaskEventOperateHandler taskEventOperateHandler,
                TaskOperateHandler taskOperateHandler
        ) {
            this.task = task;
            this.cache = cache;
            this.currentState = currentState;
            this.taskVariableOperateHandler = taskVariableOperateHandler;
            this.taskEventOperateHandler = taskEventOperateHandler;
            this.taskOperateHandler = taskOperateHandler;
        }

        @Override
        public Task getTask() {
            return task;
        }

        @Override
        public Section getSection() {
            return cache.getSection();
        }

        @Override
        public State getCurrentState() {
            return currentState;
        }

        @Nullable
        @Override
        public TaskVariableInspectResult inspectTaskVariable(TaskVariableInspectInfo info) throws Exception {
            return taskVariableOperateHandler.inspect(info);
        }

        @Override
        public void upsertTaskVariable(TaskVariableUpsertInfo info) throws Exception {
            taskVariableOperateHandler.upsert(info);
        }

        @Override
        public void removeTaskVariable(TaskVariableRemoveInfo info) throws Exception {
            taskVariableOperateHandler.remove(info);
        }

        @Override
        public void updateTaskModal(TaskUpdateModalInfo info) throws Exception {
            taskOperateHandler.updateModal(info);
        }

        @Override
        public void createTaskEvent(TaskEventCreateInfo info) throws Exception {
            taskEventOperateHandler.create(info);
        }

    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class PerformerContext implements Performer.Context {

        private final Task task;
        private final JobLocalCache cache;
        private final State anchorState;
        private final State targetState;
        private final TaskVariableOperateHandler taskVariableOperateHandler;

        public PerformerContext(
                Task task,
                JobLocalCache cache,
                State anchorState,
                State targetState,
                TaskVariableOperateHandler taskVariableOperateHandler
        ) {
            this.task = task;
            this.cache = cache;
            this.anchorState = anchorState;
            this.targetState = targetState;
            this.taskVariableOperateHandler = taskVariableOperateHandler;
        }

        @Override
        public Task getTask() {
            return task;
        }

        @Override
        public Section getSection() {
            return cache.getSection();
        }

        @Override
        public State getAnchorState() {
            return anchorState;
        }

        @Override
        public State getTargetState() {
            return targetState;
        }

        @Nullable
        @Override
        public TaskVariableInspectResult inspectTaskVariable(TaskVariableInspectInfo info) throws Exception {
            return taskVariableOperateHandler.inspect(info);
        }

        @Override
        public void upsertTaskVariable(TaskVariableUpsertInfo info) throws Exception {
            taskVariableOperateHandler.upsert(info);
        }

        @Override
        public void removeTaskVariable(TaskVariableRemoveInfo info) throws Exception {
            taskVariableOperateHandler.remove(info);
        }

    }
}
