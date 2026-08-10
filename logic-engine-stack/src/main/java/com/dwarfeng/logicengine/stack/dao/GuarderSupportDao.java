package com.dwarfeng.logicengine.stack.dao;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 守卫器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderSupportDao extends BatchBaseDao<StringIdKey, GuarderSupport>, EntireLookupDao<GuarderSupport>,
        PresetLookupDao<GuarderSupport> {
}
