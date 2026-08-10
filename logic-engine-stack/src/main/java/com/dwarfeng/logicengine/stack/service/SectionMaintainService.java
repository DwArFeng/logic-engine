package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 部件维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SectionMaintainService extends BatchCrudService<LongIdKey, Section>, EntireLookupService<Section>,
        PresetLookupService<Section> {

    // region 预设查询 - 业务逻辑

    String ENABLED = "enabled";

    // endregion
}
