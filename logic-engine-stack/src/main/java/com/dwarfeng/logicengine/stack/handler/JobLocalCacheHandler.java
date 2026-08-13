package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 作业本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface JobLocalCacheHandler extends LocalCacheHandler<LongIdKey, JobLocalCache> {
}
