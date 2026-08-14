package com.dwarfeng.logicengine.stack.struct;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.handler.Driver;

import java.util.Map;

/**
 * 驱动本地缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DriveLocalCache {

    private final Section section;
    private final Map<DriverInfo, Driver> driverMap;

    public DriveLocalCache(Section section, Map<DriverInfo, Driver> driverMap) {
        this.section = section;
        this.driverMap = driverMap;
    }

    public Section getSection() {
        return section;
    }

    public Map<DriverInfo, Driver> getDriverMap() {
        return driverMap;
    }

    @Override
    public String toString() {
        return "DriveLocalCache{" +
                "section=" + section +
                ", driverMap=" + driverMap +
                '}';
    }
}
