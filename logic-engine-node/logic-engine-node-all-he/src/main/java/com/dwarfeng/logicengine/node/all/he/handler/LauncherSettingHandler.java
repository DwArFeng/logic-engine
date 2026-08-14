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

    @Value("${com.dwarfeng.logicengine.launcher.reset_driver_support}")
    private boolean resetDriverSupport;

    @Value("${com.dwarfeng.logicengine.launcher.online_task_check_delay}")
    private long onlineTaskCheckDelay;

    @Value("${com.dwarfeng.logicengine.launcher.enable_task_check_delay}")
    private long enableTaskCheckDelay;

    @Value("${com.dwarfeng.logicengine.launcher.start_receive_delay}")
    private long startReceiveDelay;

    @Value("${com.dwarfeng.logicengine.launcher.online_supervise_delay}")
    private long onlineSuperviseDelay;

    @Value("${com.dwarfeng.logicengine.launcher.enable_supervise_delay}")
    private long enableSuperviseDelay;

    public boolean isResetPerformerSupport() {
        return resetPerformerSupport;
    }

    public boolean isResetGuarderSupport() {
        return resetGuarderSupport;
    }

    public boolean isResetDriverSupport() {
        return resetDriverSupport;
    }

    public long getOnlineTaskCheckDelay() {
        return onlineTaskCheckDelay;
    }

    public long getEnableTaskCheckDelay() {
        return enableTaskCheckDelay;
    }

    public long getStartReceiveDelay() {
        return startReceiveDelay;
    }

    public long getOnlineSuperviseDelay() {
        return onlineSuperviseDelay;
    }

    public long getEnableSuperviseDelay() {
        return enableSuperviseDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetPerformerSupport=" + resetPerformerSupport +
                ", resetGuarderSupport=" + resetGuarderSupport +
                ", resetDriverSupport=" + resetDriverSupport +
                ", onlineTaskCheckDelay=" + onlineTaskCheckDelay +
                ", enableTaskCheckDelay=" + enableTaskCheckDelay +
                ", startReceiveDelay=" + startReceiveDelay +
                ", onlineSuperviseDelay=" + onlineSuperviseDelay +
                ", enableSuperviseDelay=" + enableSuperviseDelay +
                '}';
    }
}
