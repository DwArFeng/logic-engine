package com.dwarfeng.logicengine.stack.service;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 守卫器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderSupportMaintainService extends BatchCrudService<StringIdKey, GuarderSupport>,
        EntireLookupService<GuarderSupport>, PresetLookupService<GuarderSupport> {

    // region 预设查询 - UI

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    // endregion
}
