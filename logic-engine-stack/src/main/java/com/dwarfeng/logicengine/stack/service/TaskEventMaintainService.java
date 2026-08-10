package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 任务事件维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventMaintainService extends BatchCrudService<LongIdKey, TaskEvent>,
        EntireLookupService<TaskEvent>, PresetLookupService<TaskEvent> {

    // region 预设查询 - 级联

    String CHILD_FOR_TASK = "child_for_task";

    // endregion
}
