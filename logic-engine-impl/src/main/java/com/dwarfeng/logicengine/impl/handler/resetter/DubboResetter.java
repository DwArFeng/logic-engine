package com.dwarfeng.logicengine.impl.handler.resetter;

import com.dwarfeng.logicengine.sdk.handler.resetter.AbstractResetter;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.dwarfeng.subgrade.stack.service.Service;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Dubbo 微服务实现的重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DubboResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboResetter.class);

    private final ApplicationContext ctx;

    private final RegistryConfig registry;
    private final ProtocolConfig protocol;

    @Value("${com.dwarfeng.logicengine.dubbo.provider.group}")
    private String group;

    private final Lock lock = new ReentrantLock();

    private ServiceConfig<DubboResetService> serviceConfig;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public DubboResetter(
            ApplicationContext ctx,
            RegistryConfig registry,
            @Qualifier("dubbo") ProtocolConfig protocol
    ) {
        this.ctx = ctx;
        this.registry = registry;
        this.protocol = protocol;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 重置器启动...");
            if (Objects.nonNull(serviceConfig)) {
                return;
            }

            DubboResetService dubboResetService = ctx.getBean(DubboResetService.class);

            ServiceConfig<DubboResetService> serviceConfig = new ServiceConfig<>();
            serviceConfig.setRegistry(registry);
            serviceConfig.setProtocol(protocol);
            serviceConfig.setGroup(group);
            serviceConfig.setInterface(DubboResetService.class);
            serviceConfig.setRef(dubboResetService);
            serviceConfig.export();
            this.serviceConfig = serviceConfig;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 重置器停止...");
            if (Objects.isNull(serviceConfig)) {
                return;
            }
            serviceConfig.unexport();
            serviceConfig = null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "DubboResetter{" +
                "ctx=" + ctx +
                ", registry=" + registry +
                ", protocol=" + protocol +
                ", group='" + group + '\'' +
                '}';
    }

    /**
     * Dubbo 重置服务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public interface DubboResetService extends Service {

        /**
         * 重置主管功能。
         *
         * <p>
         * 因为 Dubbo 广播响应机制无法处理 void 返回类型，所以方法需要返回一个结果。
         *
         * @return 恒为 true。
         * @throws ServiceException 服务异常。
         */
        @SuppressWarnings("SameReturnValue")
        boolean resetSupervise() throws ServiceException;

        /**
         * 重置作业功能。
         *
         * <p>
         * 因为 Dubbo 广播响应机制无法处理 void 返回类型，所以方法需要返回一个结果。
         *
         * @return 恒为 true。
         * @throws ServiceException 服务异常。
         */
        @SuppressWarnings("SameReturnValue")
        boolean resetJob() throws ServiceException;
    }

    @org.springframework.stereotype.Service("dubboResetter.dubboResetServiceImpl")
    public class DubboResetServiceImpl implements DubboResetService {

        private final ServiceExceptionMapper sem;

        public DubboResetServiceImpl(ServiceExceptionMapper sem) {
            this.sem = sem;
        }

        @Override
        public boolean resetSupervise() throws ServiceException {
            try {
                LOGGER.info("接收到主管功能重置消息, 正在重置主管功能...");
                context.resetSupervise();
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logParse("重置主管功能时发生异常", LogLevel.WARN, e, sem);
            }
        }

        @Override
        public boolean resetJob() throws ServiceException {
            try {
                LOGGER.info("接收到作业功能重置消息, 正在重置作业功能...");
                context.resetJob();
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logParse("重置作业功能时发生异常", LogLevel.WARN, e, sem);
            }
        }

        @Override
        public String toString() {
            return "DubboResetServiceImpl{" +
                    "sem=" + sem +
                    '}';
        }
    }
}
