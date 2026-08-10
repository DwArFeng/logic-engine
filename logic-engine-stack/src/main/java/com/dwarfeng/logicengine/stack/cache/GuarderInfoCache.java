package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 守卫器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderInfoCache extends BatchBaseCache<LongIdKey, GuarderInfo> {
}
