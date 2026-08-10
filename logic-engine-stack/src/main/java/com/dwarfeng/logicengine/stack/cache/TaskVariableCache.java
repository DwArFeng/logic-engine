package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 任务变量缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskVariableCache extends BatchBaseCache<TaskVariableKey, TaskVariable> {
}
