package com.dwarfeng.logicengine.impl.handler.dispatcher;

import com.dwarfeng.logicengine.impl.handler.receiver.DubboReceiver;
import com.dwarfeng.logicengine.sdk.handler.dispatcher.AbstractDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 调度器。
 *
 * <p>
 * 基于 Dubbo 实现的调度器，利用服务提供者机制将部件执行请求负载均衡到多个接收节点。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DubboDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "dubbo";

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboDispatcher.class);

    private final RegistryConfig registry;

    @Value("${com.dwarfeng.logicengine.dubbo.provider.group}")
    private String group;

    private final Lock lock = new ReentrantLock();

    private ReferenceConfig<DubboReceiver.DubboReceiveService> referenceConfig;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public DubboDispatcher(RegistryConfig registry) {
        super(DISPATCHER_TYPE);
        this.registry = registry;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 调度器开启...");
            ReferenceConfig<DubboReceiver.DubboReceiveService> referenceConfig = new ReferenceConfig<>();
            referenceConfig.setRegistry(registry);
            referenceConfig.setGroup(group);
            referenceConfig.setCheck(false);
            referenceConfig.setInterface(DubboReceiver.DubboReceiveService.class);
            referenceConfig.setScope("remote");
            this.referenceConfig = referenceConfig;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 调度器关闭...");
            if (Objects.nonNull(referenceConfig)) {
                referenceConfig.destroy();
                referenceConfig = null;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doDispatch(LongIdKey sectionKey) throws Exception {
        lock.lock();
        try {
            referenceConfig.get().execute(sectionKey);
        } finally {
            lock.unlock();
        }
    }
}
