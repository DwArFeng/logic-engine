package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 状态维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StateMaintainService extends BatchCrudService<StateKey, State>, EntireLookupService<State>,
        PresetLookupService<State> {

    // region 预设查询 - 级联

    String CHILD_FOR_SECTION = "child_for_section";

    // endregion
}
