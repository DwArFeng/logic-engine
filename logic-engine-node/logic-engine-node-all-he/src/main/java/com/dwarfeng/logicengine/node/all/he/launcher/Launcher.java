package com.dwarfeng.logicengine.node.all.he.launcher;

import com.dwarfeng.logicengine.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.logicengine.stack.service.*;
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

            // 根据启动器设置处理器的设置，选择性重置驱动器。
            mayResetDriver(ctx);

            // 根据启动器设置处理器的设置，选择性上线任务检查服务。
            mayOnlineTaskCheck(ctx);
            // 根据启动器设置处理器的设置，选择性启动任务检查服务。
            mayEnableTaskCheck(ctx);

            // 根据启动器设置处理器的设置，选择性启动接收服务。
            mayStartReceive(ctx);

            // 根据启动器设置处理器的设置，选择性上线主管服务。
            mayOnlineSupervise(ctx);
            // 根据启动器设置处理器的设置，选择性启动主管服务。
            mayEnableSupervise(ctx);

            // 根据启动器设置处理器的设置，选择性上线清除服务。
            mayOnlinePurge(ctx);
            // 根据启动器设置处理器的设置，选择性启动清除服务。
            mayEnablePurge(ctx);
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

    private static void mayResetDriver(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置驱动器，则返回。
        if (!launcherSettingHandler.isResetDriverSupport()) {
            return;
        }

        // 重置驱动器支持。
        LOGGER.info("重置驱动器支持...");
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetDriver();
        } catch (ServiceException e) {
            LOGGER.warn("驱动器支持重置失败，异常信息如下", e);
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

    private static void mayOnlineSupervise(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取主管 QoS 服务。
        SuperviseQosService superviseQosService = ctx.getBean(SuperviseQosService.class);

        // 判断主管处理器是否上线主管服务，并按条件执行不同的操作。
        long onlineSuperviseDelay = launcherSettingHandler.getOnlineSuperviseDelay();
        if (onlineSuperviseDelay == 0) {
            LOGGER.info("立即上线主管服务...");
            try {
                superviseQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线主管服务，异常原因如下", e);
            }
        } else if (onlineSuperviseDelay > 0) {
            LOGGER.info("{} 毫秒后上线主管服务...", onlineSuperviseDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线主管服务...");
                        try {
                            superviseQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线主管服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineSuperviseDelay)
            );
        }
    }

    private static void mayEnableSupervise(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取主管 QoS 服务。
        SuperviseQosService superviseQosService = ctx.getBean(SuperviseQosService.class);

        // 判断主管处理器是否启动主管服务，并按条件执行不同的操作。
        long enableSuperviseDelay = launcherSettingHandler.getEnableSuperviseDelay();
        if (enableSuperviseDelay == 0) {
            LOGGER.info("立即启动主管服务...");
            try {
                superviseQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动主管服务，异常原因如下", e);
            }
        } else if (enableSuperviseDelay > 0) {
            LOGGER.info("{} 毫秒后启动主管服务...", enableSuperviseDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动主管服务...");
                        try {
                            superviseQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动主管服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableSuperviseDelay)
            );
        }
    }

    private static void mayOnlinePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);
        // 获取清除 QoS 服务。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 判断清除服务是否上线，并按条件执行不同的操作。
        long onlinePurgeDelay = launcherSettingHandler.getOnlinePurgeDelay();
        if (onlinePurgeDelay == 0) {
            LOGGER.info("立即上线清除服务...");
            try {
                purgeQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线清除服务，异常原因如下", e);
            }
        } else if (onlinePurgeDelay > 0) {
            LOGGER.info("{} 毫秒后上线清除服务...", onlinePurgeDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线清除服务...");
                        try {
                            purgeQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线清除服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlinePurgeDelay)
            );
        }
    }

    private static void mayEnablePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);
        // 获取清除 QoS 服务。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 判断清除服务是否启动，并按条件执行不同的操作。
        long enablePurgeDelay = launcherSettingHandler.getEnablePurgeDelay();
        if (enablePurgeDelay == 0) {
            LOGGER.info("立即启动清除服务...");
            try {
                purgeQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动清除服务，异常原因如下", e);
            }
        } else if (enablePurgeDelay > 0) {
            LOGGER.info("{} 毫秒后启动清除服务...", enablePurgeDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动清除服务...");
                        try {
                            purgeQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动清除服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enablePurgeDelay)
            );
        }
    }
}
