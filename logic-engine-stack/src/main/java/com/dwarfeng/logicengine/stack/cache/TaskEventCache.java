package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 任务事件缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventCache extends BatchBaseCache<LongIdKey, TaskEvent> {
}
