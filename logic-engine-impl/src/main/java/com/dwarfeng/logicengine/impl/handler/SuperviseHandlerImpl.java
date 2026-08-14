package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.handler.DispatchHandler;
import com.dwarfeng.logicengine.stack.handler.DriveHandler;
import com.dwarfeng.logicengine.stack.handler.SuperviseHandler;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SuperviseHandlerImpl implements SuperviseHandler {

    private final CuratorDistributedLockHandler handler;

    public SuperviseHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${com.dwarfeng.logicengine.curator.latch_path.supervise.leader_latch}") String leaderLatchPath,
            SuperviseWorker superviseWorker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaderLatchPath, superviseWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
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

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    /**
     * 主管工作器。
     *
     * <p>
     * 该工作器在主管开始工作时启动调度和驱动机制，在主管停止工作时按逆序停止驱动和调度机制。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    public static class SuperviseWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(SuperviseWorker.class);

        private final DispatchHandler dispatchHandler;
        private final DriveHandler driveHandler;

        public SuperviseWorker(DispatchHandler dispatchHandler, DriveHandler driveHandler) {
            this.dispatchHandler = dispatchHandler;
            this.driveHandler = driveHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("主管处理器开始工作...");
            dispatchHandler.start();
            driveHandler.start();
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("主管处理器停止工作...");
            driveHandler.stop();
            dispatchHandler.stop();
        }
    }
}
