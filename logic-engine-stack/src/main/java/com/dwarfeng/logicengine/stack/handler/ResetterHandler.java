package com.dwarfeng.logicengine.stack.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 重置器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ResetterHandler extends Handler {

    /**
     * 获取所有重置器。
     *
     * @return 所有重置器组成的列表。
     */
    List<Resetter> all();
}
