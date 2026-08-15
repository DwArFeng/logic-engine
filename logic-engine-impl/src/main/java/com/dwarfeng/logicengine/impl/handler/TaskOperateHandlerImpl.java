package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.*;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.handler.PushHandler;
import com.dwarfeng.logicengine.stack.handler.TaskOperateHandler;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class TaskOperateHandlerImpl implements TaskOperateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskOperateHandlerImpl.class);

    private static final Set<Integer> VALID_TASK_STATUS_SET_START;
    private static final Set<Integer> VALID_TASK_STATUS_SET_FINISH;
    private static final Set<Integer> VALID_TASK_STATUS_SET_FAIL;
    private static final Set<Integer> VALID_TASK_STATUS_SET_EXPIRE;
    private static final Set<Integer> VALID_TASK_STATUS_SET_DEAD;
    private static final Set<Integer> VALID_TASK_STATUS_SET_UPDATE_MODAL;
    private static final Set<Integer> VALID_TASK_STATUS_SET_BEAT;
    private static final Set<Integer> VALID_TASK_STATUS_SET_CHANGE_STATE;

    static {
        Set<Integer> VALID_TASK_STATUS_SET_START_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_START_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_START = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_START_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_FINISH_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_FINISH_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_FINISH_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_FINISH = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_FINISH_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_FAIL_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_FAIL_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_FAIL_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_FAIL = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_FAIL_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_EXPIRE = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_DEAD_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_DEAD_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_DEAD = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_DEAD_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_UPDATE_MODAL = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_BEAT_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_BEAT_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_BEAT = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_BEAT_DEJA_VU
        );

        Set<Integer> VALID_TASK_STATUS_SET_CHANGE_STATE_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_CHANGE_STATE_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_CHANGE_STATE = Collections.unmodifiableSet(
                VALID_TASK_STATUS_SET_CHANGE_STATE_DEJA_VU
        );
    }

    private final TaskMaintainService taskMaintainService;

    private final SectionMaintainService sectionMaintainService;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final HandlerValidator handlerValidator;

    private final PushHandler pushHandler;

    @Value("${com.dwarfeng.logicengine.task.die_timeout}")
    private long dieTimeout;

    public TaskOperateHandlerImpl(
            TaskMaintainService taskMaintainService,
            SectionMaintainService sectionMaintainService,
            KeyGenerator<LongIdKey> keyGenerator,
            HandlerValidator handlerValidator,
            PushHandler pushHandler
    ) {
        this.taskMaintainService = taskMaintainService;
        this.sectionMaintainService = sectionMaintainService;
        this.keyGenerator = keyGenerator;
        this.handlerValidator = handlerValidator;
        this.pushHandler = pushHandler;
    }

    @Override
    public TaskCreateResult create(TaskCreateInfo createInfo) throws HandlerException {
        try {
            return create0(createInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    private TaskCreateResult create0(TaskCreateInfo createInfo) throws Exception {
        // 展开参数。
        LongIdKey sectionKey = createInfo.getSectionKey();

        // 确认部件存在。
        handlerValidator.makeSureSectionExists(sectionKey);

        // 获取部件。
        Section section = sectionMaintainService.get(sectionKey);

        // 创建任务实体。
        LongIdKey taskKey = keyGenerator.generate();
        Date currentDate = new Date();
        Date expireDate;
        if (section.getExpireTimeout() <= 0) {
            expireDate = new Date(Long.MAX_VALUE);
        } else {
            expireDate = new Date(currentDate.getTime() + section.getExpireTimeout());
        }
        Task task = new Task(
                taskKey,
                sectionKey,
                null,
                Constants.TASK_STATUS_CREATED,
                currentDate,
                null,
                null,
                0L,
                expireDate,
                null,
                null,
                null,
                null,
                null
        );

        // 调用维护服务插入任务实体。
        taskMaintainService.insert(task);

        // 生成结果并返回。
        return new TaskCreateResult(taskKey);
    }

    @Override
    public void start(TaskStartInfo startInfo) throws HandlerException {
        try {
            start0(startInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void start0(TaskStartInfo startInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = startInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_START);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务字段。
        Date currentDate = new Date();
        task.setStatus(Constants.TASK_STATUS_PROCESSING);
        task.setStartedDate(currentDate);
        task.setShouldDieDate(new Date(currentDate.getTime() + dieTimeout));

        // 调用维护服务更新任务实体。
        taskMaintainService.update(task);
    }

    @Override
    public void finish(TaskFinishInfo finishInfo) throws HandlerException {
        try {
            finish0(finishInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void finish0(TaskFinishInfo finishInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = finishInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_FINISH);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务状态。
        Date currentDate = new Date();
        task.setStatus(Constants.TASK_STATUS_FINISHED);
        task.setEndedDate(currentDate);
        task.setDuration(duration(task, currentDate));

        // 调用维护服务更新任务状态。
        taskMaintainService.update(task);

        // 推送任务完成事件。
        pushTaskEvent(task, Constants.TASK_STATUS_FINISHED);
    }

    @Override
    public void fail(TaskFailInfo failInfo) throws HandlerException {
        try {
            fail0(failInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void fail0(TaskFailInfo failInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = failInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_FAIL);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务状态。
        Date currentDate = new Date();
        task.setStatus(Constants.TASK_STATUS_FAILED);
        task.setEndedDate(currentDate);
        task.setDuration(duration(task, currentDate));

        // 调用维护服务更新任务状态。
        taskMaintainService.update(task);

        // 推送任务失败事件。
        pushTaskEvent(task, Constants.TASK_STATUS_FAILED);
    }

    @Override
    public void expire(TaskExpireInfo expireInfo) throws HandlerException {
        try {
            expire0(expireInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void expire0(TaskExpireInfo expireInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = expireInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_EXPIRE);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务状态。
        Date currentDate = new Date();
        task.setStatus(Constants.TASK_STATUS_EXPIRED);
        task.setEndedDate(currentDate);
        task.setDuration(duration(task, currentDate));
        task.setExpiredDate(currentDate);

        // 调用维护服务更新任务状态。
        taskMaintainService.update(task);

        // 推送任务过期事件。
        pushTaskEvent(task, Constants.TASK_STATUS_EXPIRED);
    }

    @Override
    public void die(TaskDieInfo dieInfo) throws HandlerException {
        try {
            die0(dieInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void die0(TaskDieInfo dieInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = dieInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_DEAD);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务状态。
        Date currentDate = new Date();
        task.setStatus(Constants.TASK_STATUS_DIED);
        task.setEndedDate(currentDate);
        task.setDuration(duration(task, currentDate));
        task.setDiedDate(currentDate);

        // 调用维护服务更新任务状态。
        taskMaintainService.update(task);

        // 推送任务死亡事件。
        pushTaskEvent(task, Constants.TASK_STATUS_DIED);
    }

    @Override
    public void updateModal(TaskUpdateModalInfo updateModalInfo) throws HandlerException {
        try {
            updateModal0(updateModalInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void updateModal0(TaskUpdateModalInfo updateModalInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = updateModalInfo.getTaskKey();
        String anchorMessage = updateModalInfo.getAnchorMessage();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_UPDATE_MODAL);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务字段。
        task.setAnchorMessage(anchorMessage);

        // 调用维护服务更新任务实体。
        taskMaintainService.update(task);
    }

    @Override
    public void beat(TaskBeatInfo beatInfo) throws HandlerException {
        try {
            beat0(beatInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void beat0(TaskBeatInfo beatInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = beatInfo.getTaskKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_BEAT);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务字段。
        Date currentDate = new Date();
        task.setShouldDieDate(new Date(currentDate.getTime() + dieTimeout));

        // 调用维护服务更新任务实体。
        taskMaintainService.update(task);
    }

    @Override
    public void changeState(TaskChangeStateInfo changeStateInfo) throws HandlerException {
        try {
            changeState0(changeStateInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void changeState0(TaskChangeStateInfo changeStateInfo) throws Exception {
        // 展开参数。
        LongIdKey taskKey = changeStateInfo.getTaskKey();
        StateKey stateKey = changeStateInfo.getStateKey();

        // 确认任务存在。
        handlerValidator.makeSureTaskExists(taskKey);
        // 确认任务状态合法。
        handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_CHANGE_STATE);

        // 获取任务。
        Task task = taskMaintainService.get(taskKey);

        // 更新任务字段。
        task.setCurrentStateKey(stateKey);
        task.setStateChangedDate(new Date());

        // 调用维护服务更新任务实体。
        taskMaintainService.update(task);
    }

    private void pushTaskEvent(Task task, int status) {
        try {
            Section section = sectionMaintainService.get(task.getSectionKey());
            switch (status) {
                case Constants.TASK_STATUS_FINISHED:
                    pushHandler.taskFinished(section);
                    break;
                case Constants.TASK_STATUS_FAILED:
                    pushHandler.taskFailed(section);
                    break;
                case Constants.TASK_STATUS_EXPIRED:
                    pushHandler.taskExpired(section);
                    break;
                case Constants.TASK_STATUS_DIED:
                    pushHandler.taskDied(section);
                    break;
                default:
                    throw new IllegalArgumentException("未知的任务终结状态: " + status);
            }
        } catch (Exception e) {
            LOGGER.warn("推送任务终结消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    private static Long duration(Task task, Date endedDate) {
        Date startDate = task.getStartedDate() == null ? task.getCreatedDate() : task.getStartedDate();
        return startDate == null ? null : endedDate.getTime() - startDate.getTime();
    }
}
