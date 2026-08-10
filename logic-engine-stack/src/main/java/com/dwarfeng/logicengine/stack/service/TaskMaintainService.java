package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 任务维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskMaintainService extends BatchCrudService<LongIdKey, Task>, EntireLookupService<Task>,
        PresetLookupService<Task> {

    // region 预设查询 - 级联

    String CHILD_FOR_SECTION = "child_for_section";
    String CHILD_FOR_CURRENT_STATE = "child_for_current_state";

    // endregion

    // region 预设查询 - 业务逻辑

    String SHOULD_EXPIRE = "should_expire";
    String SHOULD_DIE = "should_die";

    // endregion
}
