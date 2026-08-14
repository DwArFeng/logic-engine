package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.struct.DriveLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 驱动用本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;LongIdKey, DriveLocalCache&gt;</code> 接口，
 * 用于处理与驱动相关的本地缓存。<br>
 * 对于 <code>LocalCacheHandler&lt;K, V&gt;</code> 接口中的泛型参数:
 * <table>
 *     <tr>
 *         <th></th>
 *         <th>类型</th>
 *         <th>含义</th>
 *     </tr>
 *     <tr>
 *         <td>K</td>
 *         <td>LongIdKey</td>
 *         <td>部件主键</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>DriveLocalCache</td>
 *         <td>驱动本地缓存</td>
 *     </tr>
 * </table>
 *
 * @author DwArFeng
 * @see LocalCacheHandler
 * @see DriveLocalCache
 * @since 1.0.0
 */
public interface DriveLocalCacheHandler extends LocalCacheHandler<LongIdKey, DriveLocalCache> {
}
