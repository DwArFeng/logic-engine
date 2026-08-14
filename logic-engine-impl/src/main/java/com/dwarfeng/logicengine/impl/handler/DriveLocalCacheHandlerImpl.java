package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.logicengine.stack.handler.Driver;
import com.dwarfeng.logicengine.stack.handler.DriverHandler;
import com.dwarfeng.logicengine.stack.service.DriverInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.struct.DriveLocalCache;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DriveLocalCacheHandlerImpl implements DriveLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, DriveLocalCache> handler;

    public DriveLocalCacheHandlerImpl(DriveLocalCacheFetcher driveLocalCacheFetcher) {
        handler = new GeneralLocalCacheHandler<>(driveLocalCacheFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public DriveLocalCache get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class DriveLocalCacheFetcher implements Fetcher<LongIdKey, DriveLocalCache> {

        private final SectionMaintainService sectionMaintainService;
        private final DriverInfoMaintainService driverInfoMaintainService;

        private final DriverHandler driverHandler;

        public DriveLocalCacheFetcher(
                SectionMaintainService sectionMaintainService,
                DriverInfoMaintainService driverInfoMaintainService,
                DriverHandler driverHandler
        ) {
            this.sectionMaintainService = sectionMaintainService;
            this.driverInfoMaintainService = driverInfoMaintainService;
            this.driverHandler = driverHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            Section section = sectionMaintainService.getIfExists(key);
            if (Objects.isNull(section)) {
                return false;
            }
            return section.isEnabled();
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public DriveLocalCache fetch(LongIdKey key) throws Exception {
            Section section = sectionMaintainService.get(key);
            List<DriverInfo> driverInfos = driverInfoMaintainService.lookupAsList(
                    DriverInfoMaintainService.CHILD_FOR_SECTION_ENABLED, new Object[]{key}
            );

            Map<DriverInfo, Driver> driverMap = new HashMap<>();
            for (DriverInfo driverInfo : driverInfos) {
                driverMap.put(driverInfo, driverHandler.find(driverInfo.getType()));
            }

            return new DriveLocalCache(section, driverMap);
        }
    }
}
