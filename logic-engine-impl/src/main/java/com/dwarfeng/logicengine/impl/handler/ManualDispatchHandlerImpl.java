package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.logicengine.stack.handler.DispatcherHandler;
import com.dwarfeng.logicengine.stack.handler.ManualDispatchHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 手动调度处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
@Component
public class ManualDispatchHandlerImpl implements ManualDispatchHandler {

    private final DispatcherHandler dispatcherHandler;

    public ManualDispatchHandlerImpl(DispatcherHandler dispatcherHandler) {
        this.dispatcherHandler = dispatcherHandler;
    }

    @Override
    public void dispatch(ManualDispatchInfo info) throws HandlerException {
        try {
            dispatch0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void dispatch0(ManualDispatchInfo info) throws Exception {
        Objects.requireNonNull(info, "手动调度信息不能为 null");

        LongIdKey sectionKey = Objects.requireNonNull(info.getSectionKey(), "部件主键不能为 null");
        dispatcherHandler.current().dispatch(sectionKey);
    }
}
