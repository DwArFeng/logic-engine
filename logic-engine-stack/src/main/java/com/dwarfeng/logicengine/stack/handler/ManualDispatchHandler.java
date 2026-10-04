package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 手动调度处理器。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
public interface ManualDispatchHandler extends Handler {

    /**
     * 手动调度指定部件。
     *
     * @param info 手动调度信息。
     * @throws HandlerException 处理器异常。
     */
    void dispatch(ManualDispatchInfo info) throws HandlerException;
}
