package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 驱动器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverInfoMaintainService extends BatchCrudService<LongIdKey, DriverInfo>,
        EntireLookupService<DriverInfo>, PresetLookupService<DriverInfo> {

    // region 预设查询 - 级联

    String CHILD_FOR_SECTION = "child_for_section";

    // endregion

    // region 预设查询 - 业务逻辑

    String CHILD_FOR_SECTION_ENABLED = "child_for_section_enabled";

    // endregion
}
