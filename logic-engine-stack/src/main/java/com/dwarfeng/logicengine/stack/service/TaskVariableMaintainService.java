package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 任务变量维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskVariableMaintainService extends BatchCrudService<TaskVariableKey, TaskVariable>,
        EntireLookupService<TaskVariable>, PresetLookupService<TaskVariable> {

    // region 预设查询 - 级联

    String CHILD_FOR_TASK = "child_for_task";

    // endregion

}
