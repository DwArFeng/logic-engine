package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 执行器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PerformerInfoCache extends BatchBaseCache<LongIdKey, PerformerInfo> {
}
