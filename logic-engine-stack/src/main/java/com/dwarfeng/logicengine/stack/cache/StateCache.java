package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 状态缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StateCache extends BatchBaseCache<StateKey, State> {
}
