package com.dwarfeng.logicengine.stack.dao;

import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 任务变量数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskVariableDao extends BatchBaseDao<TaskVariableKey, TaskVariable>, EntireLookupDao<TaskVariable>,
        PresetLookupDao<TaskVariable> {
}
