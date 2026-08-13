package com.dwarfeng.logicengine.node.all.he.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.logicengine.launcher.reset_performer_support}")
    private boolean resetPerformerSupport;

    @Value("${com.dwarfeng.logicengine.launcher.reset_guarder_support}")
    private boolean resetGuarderSupport;

    public boolean isResetPerformerSupport() {
        return resetPerformerSupport;
    }

    public boolean isResetGuarderSupport() {
        return resetGuarderSupport;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetPerformerSupport=" + resetPerformerSupport +
                ", resetGuarderSupport=" + resetGuarderSupport +
                '}';
    }
}
