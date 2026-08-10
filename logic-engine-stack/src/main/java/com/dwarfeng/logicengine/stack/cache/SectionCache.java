package com.dwarfeng.logicengine.stack.cache;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 部件缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SectionCache extends BatchBaseCache<LongIdKey, Section> {
}
