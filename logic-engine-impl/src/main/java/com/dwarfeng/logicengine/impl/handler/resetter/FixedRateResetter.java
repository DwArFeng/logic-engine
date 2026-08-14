package com.dwarfeng.logicengine.impl.handler.resetter;

import com.dwarfeng.logicengine.sdk.handler.resetter.AbstractResetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * 固定频率的重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FixedRateResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedRateResetter.class);

    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.logicengine.resetter.fixed_rate.rate}")
    private long rate;

    private final ResetTask resetTask = new ResetTask();

    private ScheduledFuture<?> resetTaskFuture;

    public FixedRateResetter(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void doStart() {
        resetTaskFuture = scheduler.scheduleAtFixedRate(resetTask, rate);
    }

    @Override
    protected void doStop() {
        if (Objects.nonNull(resetTaskFuture)) {
            resetTaskFuture.cancel(true);
            resetTaskFuture = null;
        }
    }

    @Override
    public String toString() {
        return "FixedRateResetter{" +
                "rate=" + rate +
                '}';
    }

    private class ResetTask implements Runnable {

        @Override
        public void run() {
            try {
                LOGGER.info("计划时间已到, 重置相关功能...");
                LOGGER.info("重置主管功能...");
                context.resetSupervise();
                LOGGER.info("重置作业功能...");
                context.resetJob();
            } catch (Exception e) {
                String message = "重置器 " + FixedRateResetter.this +
                        " 执行重置调度时发生异常, 相关功能可能不会完全重置, 异常信息如下: ";
                LOGGER.warn(message, e);
            }
        }
    }
}
