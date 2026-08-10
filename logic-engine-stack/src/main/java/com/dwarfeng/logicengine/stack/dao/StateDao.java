package com.dwarfeng.logicengine.stack.dao;

import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 状态数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StateDao extends BatchBaseDao<StateKey, State>, EntireLookupDao<State>, PresetLookupDao<State> {
}
