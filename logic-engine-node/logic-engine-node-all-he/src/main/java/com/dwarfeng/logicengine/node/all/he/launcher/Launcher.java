package com.dwarfeng.logicengine.node.all.he.launcher;

import com.dwarfeng.logicengine.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.logicengine.stack.service.SupportQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

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
}
