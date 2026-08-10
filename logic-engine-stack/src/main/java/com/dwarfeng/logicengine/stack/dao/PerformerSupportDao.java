package com.dwarfeng.logicengine.stack.dao;

import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 执行器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PerformerSupportDao extends BatchBaseDao<StringIdKey, PerformerSupport>,
        EntireLookupDao<PerformerSupport>, PresetLookupDao<PerformerSupport> {
}
