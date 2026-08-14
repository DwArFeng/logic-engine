package com.dwarfeng.logicengine.impl.handler.resetter;

import com.dwarfeng.logicengine.sdk.handler.resetter.AbstractResetter;
import org.springframework.stereotype.Component;

/**
 * 永不执行重置的重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class NeverResetter extends AbstractResetter {

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
    }

    @Override
    public String toString() {
        return "NeverResetter{}";
    }
}
