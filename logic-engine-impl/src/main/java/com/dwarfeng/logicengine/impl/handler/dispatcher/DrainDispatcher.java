package com.dwarfeng.logicengine.impl.handler.dispatcher;

import com.dwarfeng.logicengine.sdk.handler.dispatcher.AbstractDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 丢弃所有调度请求的调度器。
 *
 * <p>
 * 该调度器用于测试和调试，不应该在生产环境中使用。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "drain";

    private static final Logger LOGGER = LoggerFactory.getLogger(DrainDispatcher.class);

    public DrainDispatcher() {
        super(DISPATCHER_TYPE);
    }

    @Override
    protected void doStart() {
        LOGGER.info("Drain 调度器启动, 该调度器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Drain 调度器停止, 该调度器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    protected void doDispatch(LongIdKey sectionKey) {
        LOGGER.info("Drain 调度器接到调度请求并丢弃, 部件主键: {}", sectionKey);
    }

    @Override
    public String toString() {
        return "DrainDispatcher{" +
                "dispatcherType='" + dispatcherType + '\'' +
                '}';
    }
}
