package com.dwarfeng.logicengine.stack.dao;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 驱动器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverInfoDao extends BatchBaseDao<LongIdKey, DriverInfo>, EntireLookupDao<DriverInfo>,
        PresetLookupDao<DriverInfo> {
}
