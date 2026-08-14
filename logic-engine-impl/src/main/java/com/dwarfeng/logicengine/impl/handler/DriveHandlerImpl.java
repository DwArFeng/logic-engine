package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.exception.DriverException;
import com.dwarfeng.logicengine.stack.handler.DriveHandler;
import com.dwarfeng.logicengine.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.logicengine.stack.handler.Driver;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.struct.DriveLocalCache;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DriveHandlerImpl implements DriveHandler {

    private final GeneralStartableHandler handler;

    public DriveHandlerImpl(DriveWorker driveWorker) {
        handler = new GeneralStartableHandler(driveWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @Component
    public static class DriveWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(DriveWorker.class);

        private final SectionMaintainService sectionMaintainService;

        private final DriveLocalCacheHandler driveLocalCacheHandler;

        private final Set<Driver> usedDrivers = new HashSet<>();

        public DriveWorker(
                SectionMaintainService sectionMaintainService,
                DriveLocalCacheHandler driveLocalCacheHandler
        ) {
            this.sectionMaintainService = sectionMaintainService;
            this.driveLocalCacheHandler = driveLocalCacheHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("驱动器开始工作...");

            List<Section> sections = sectionMaintainService.lookupAsList(
                    SectionMaintainService.ENABLED, new Object[]{}
            );
            boolean successFlag = true;
            for (Section section : sections) {
                DriveLocalCache driveLocalCache = driveLocalCacheHandler.get(section.getKey());
                if (Objects.isNull(driveLocalCache)) {
                    throw new DriverException("无法在本地缓存中找到有效的驱动上下文: " + section.getKey());
                }
                if (!registerDriver(driveLocalCache)) {
                    successFlag = false;
                }
            }
            if (successFlag) {
                LOGGER.info("所有驱动器信息注册成功");
            } else {
                LOGGER.warn("至少一条驱动器信息注册失败，请查看警报日志以了解详细原因");
            }
        }

        private boolean registerDriver(DriveLocalCache driveLocalCache) {
            boolean successFlag = true;
            Map<DriverInfo, Driver> driverMap = driveLocalCache.getDriverMap();
            for (Map.Entry<DriverInfo, Driver> entry : driverMap.entrySet()) {
                DriverInfo driverInfo = entry.getKey();
                Driver driver = entry.getValue();
                try {
                    driver.register(driverInfo);
                    usedDrivers.add(driver);
                } catch (Exception e) {
                    successFlag = false;
                    LOGGER.warn("驱动器信息 {} 注册失败，将忽略此条注册信息", driverInfo, e);
                }
            }
            return successFlag;
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("驱动器停止工作...");

            try {
                for (Driver driver : usedDrivers) {
                    driver.unregisterAll();
                }
            } finally {
                usedDrivers.clear();
            }
        }
    }
}
