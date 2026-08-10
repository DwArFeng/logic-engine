package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.logicengine.stack.handler.TaskEventOperateHandler;
import com.dwarfeng.logicengine.stack.service.TaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskEventOperateHandlerImpl implements TaskEventOperateHandler {

    private final HandlerValidator handlerValidator;
    private final TaskEventMaintainService taskEventMaintainService;

    public TaskEventOperateHandlerImpl(
            HandlerValidator handlerValidator,
            TaskEventMaintainService taskEventMaintainService
    ) {
        this.handlerValidator = handlerValidator;
        this.taskEventMaintainService = taskEventMaintainService;
    }

    @Override
    public TaskEventCreateResult create(TaskEventCreateInfo info) throws HandlerException {
        try {
            LongIdKey taskKey = info.getTaskKey();
            handlerValidator.makeSureTaskExists(taskKey);
            Date happenedDate = info.getHappenedDate() == null ? new Date() : info.getHappenedDate();
            TaskEvent taskEvent = new TaskEvent(null, taskKey, happenedDate, info.getMessage());
            LongIdKey taskEventKey = taskEventMaintainService.insertOrUpdate(taskEvent);
            return new TaskEventCreateResult(taskEventKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
