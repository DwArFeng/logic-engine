package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 守卫器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface GuarderSupportCache extends BatchBaseCache<StringIdKey, GuarderSupport> {
}
