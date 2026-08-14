package com.dwarfeng.logicengine.node.all.he.launcher;

import com.dwarfeng.logicengine.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.logicengine.stack.service.ReceiveQosService;
import com.dwarfeng.logicengine.stack.service.SupportQosService;
import com.dwarfeng.logicengine.stack.service.TaskCheckQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置执行器。
            mayResetPerformer(ctx);

            // 根据启动器设置处理器的设置，选择性重置守卫器。
            mayResetGuarder(ctx);

            // 根据启动器设置处理器的设置，选择性上线任务检查服务。
            mayOnlineTaskCheck(ctx);
            // 根据启动器设置处理器的设置，选择性启动任务检查服务。
            mayEnableTaskCheck(ctx);

            // 根据启动器设置处理器的设置，选择性启动接收服务。
            mayStartReceive(ctx);
        });
    }

    private static void mayResetPerformer(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置执行器，则返回。
        if (!launcherSettingHandler.isResetPerformerSupport()) {
            return;
        }

        // 重置执行器支持。
        LOGGER.info("重置执行器支持...");
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetPerformer();
        } catch (ServiceException e) {
            LOGGER.warn("执行器支持重置失败，异常信息如下", e);
        }
    }

    private static void mayResetGuarder(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置守卫器，则返回。
        if (!launcherSettingHandler.isResetGuarderSupport()) {
            return;
        }

        // 重置守卫器支持。
        LOGGER.info("重置守卫器支持...");
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetGuarder();
        } catch (ServiceException e) {
            LOGGER.warn("守卫器支持重置失败，异常信息如下", e);
        }
    }

    private static void mayOnlineTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否上线任务检查服务，并按条件执行不同的操作。
        long onlineTaskCheckDelay = launcherSettingHandler.getOnlineTaskCheckDelay();
        if (onlineTaskCheckDelay == 0) {
            LOGGER.info("立即上线任务检查服务...");
            try {
                taskCheckQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线任务检查服务，异常原因如下", e);
            }
        } else if (onlineTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后上线任务检查服务...", onlineTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线任务检查服务...");
                        try {
                            taskCheckQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineTaskCheckDelay)
            );
        }
    }

    private static void mayEnableTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否启动任务检查服务，并按条件执行不同的操作。
        long enableTaskCheckDelay = launcherSettingHandler.getEnableTaskCheckDelay();
        if (enableTaskCheckDelay == 0) {
            LOGGER.info("立即启动任务检查服务...");
            try {
                taskCheckQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动任务检查服务，异常原因如下", e);
            }
        } else if (enableTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后启动任务检查服务...", enableTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动任务检查服务...");
                        try {
                            taskCheckQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableTaskCheckDelay)
            );
        }
    }

    private static void mayStartReceive(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取接收 QoS 服务。
        ReceiveQosService receiveQosService = ctx.getBean(ReceiveQosService.class);

        // 判断接收处理器是否启动接收服务，并按条件执行不同的操作。
        long startReceiveDelay = launcherSettingHandler.getStartReceiveDelay();
        if (startReceiveDelay == 0) {
            LOGGER.info("立即启动接收服务...");
            try {
                receiveQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动接收服务，异常原因如下", e);
            }
        } else if (startReceiveDelay > 0) {
            LOGGER.info("{} 毫秒后启动接收服务...", startReceiveDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动接收服务...");
                        try {
                            receiveQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动接收服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startReceiveDelay)
            );
        }
    }
}
