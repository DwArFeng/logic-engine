package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 守卫器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderInfoMaintainService extends BatchCrudService<LongIdKey, GuarderInfo>,
        EntireLookupService<GuarderInfo>, PresetLookupService<GuarderInfo> {

    // region 预设查询 - 级联

    String CHILD_FOR_SECTION = "child_for_section";
    String CHILD_FOR_ANCHOR_STATE = "child_for_anchor_state";
    String CHILD_FOR_TARGET_STATE = "child_for_target_state";

    // endregion

    // region 预设查询 - UI

    /**
     * @since 1.2.0
     */
    String SECTION_KEY_ASC_INDEX_ASC = "section_key_asc_index_asc";

    /**
     * @since 1.2.0
     */
    String CHILD_FOR_SECTION_INDEX_ASC = "child_for_section_index_asc";

    // endregion
}
