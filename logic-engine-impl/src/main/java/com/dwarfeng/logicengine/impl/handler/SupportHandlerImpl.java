package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.handler.PerformerSupporter;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.logicengine.stack.handler.SupportHandler;
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

    private final PerformerSupportMaintainService performerSupportMaintainService;

    private final List<PerformerSupporter> performerSupporters;

    public SupportHandlerImpl(
            PerformerSupportMaintainService performerSupportMaintainService,
            List<PerformerSupporter> performerSupporters
    ) {
        this.performerSupportMaintainService = performerSupportMaintainService;
        this.performerSupporters = performerSupporters;
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
