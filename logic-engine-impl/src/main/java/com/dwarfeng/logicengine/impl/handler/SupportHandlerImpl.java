package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.handler.DriverSupporter;
import com.dwarfeng.logicengine.sdk.handler.GuarderSupporter;
import com.dwarfeng.logicengine.sdk.handler.PerformerSupporter;
import com.dwarfeng.logicengine.stack.bean.entity.DriverSupport;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.logicengine.stack.handler.SupportHandler;
import com.dwarfeng.logicengine.stack.service.DriverSupportMaintainService;
import com.dwarfeng.logicengine.stack.service.GuarderSupportMaintainService;
import com.dwarfeng.logicengine.stack.service.PerformerSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final DriverSupportMaintainService driverSupportMaintainService;
    private final GuarderSupportMaintainService guarderSupportMaintainService;
    private final PerformerSupportMaintainService performerSupportMaintainService;

    private final List<DriverSupporter> driverSupporters;
    private final List<GuarderSupporter> guarderSupporters;
    private final List<PerformerSupporter> performerSupporters;

    public SupportHandlerImpl(
            DriverSupportMaintainService driverSupportMaintainService,
            GuarderSupportMaintainService guarderSupportMaintainService,
            PerformerSupportMaintainService performerSupportMaintainService,
            List<DriverSupporter> driverSupporters,
            List<GuarderSupporter> guarderSupporters,
            List<PerformerSupporter> performerSupporters
    ) {
        this.driverSupportMaintainService = driverSupportMaintainService;
        this.guarderSupportMaintainService = guarderSupportMaintainService;
        this.performerSupportMaintainService = performerSupportMaintainService;
        this.driverSupporters = driverSupporters;
        this.guarderSupporters = guarderSupporters;
        this.performerSupporters = performerSupporters;
    }

    @Override
    @BehaviorAnalyse
    public void resetDriver() throws HandlerException {
        try {
            doResetDriver();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetDriver() throws Exception {
        // 清除现有驱动器支持信息。
        List<StringIdKey> driverKeys = driverSupportMaintainService.lookupAsList().stream()
                .map(DriverSupport::getKey).collect(Collectors.toList());
        driverSupportMaintainService.batchDelete(driverKeys);
        // 根据当前注册的驱动器支持器重新生成驱动器支持信息。
        List<DriverSupport> driverSupports = driverSupporters.stream().map(
                supporter -> new DriverSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        driverSupportMaintainService.batchInsert(driverSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetGuarder() throws HandlerException {
        try {
            doResetGuarder();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetGuarder() throws Exception {
        // 清除现有守卫器支持信息。
        List<StringIdKey> guarderKeys = guarderSupportMaintainService.lookupAsList().stream()
                .map(GuarderSupport::getKey).collect(Collectors.toList());
        guarderSupportMaintainService.batchDelete(guarderKeys);
        // 根据当前注册的守卫器支持器重新生成守卫器支持信息。
        List<GuarderSupport> guarderSupports = guarderSupporters.stream().map(
                supporter -> new GuarderSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        guarderSupportMaintainService.batchInsert(guarderSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetPerformer() throws HandlerException {
        try {
            doResetPerformer();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetPerformer() throws Exception {
        // 清除现有执行器支持信息。
        List<StringIdKey> performerKeys = performerSupportMaintainService.lookupAsList().stream()
                .map(PerformerSupport::getKey).collect(Collectors.toList());
        performerSupportMaintainService.batchDelete(performerKeys);
        // 根据当前注册的执行器支持器重新生成执行器支持信息。
        List<PerformerSupport> performerSupports = performerSupporters.stream().map(
                supporter -> new PerformerSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        performerSupportMaintainService.batchInsert(performerSupports);
    }
}
