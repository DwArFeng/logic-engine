package com.dwarfeng.logicengine.sdk.handler.performer;

import com.dwarfeng.logicengine.sdk.handler.PerformerMaker;
import com.dwarfeng.logicengine.sdk.handler.PerformerSupporter;

import java.util.Objects;

/**
 * 抽象执行器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractPerformerRegistry implements PerformerMaker, PerformerSupporter {

    protected String performerType;

    public AbstractPerformerRegistry() {
    }

    public AbstractPerformerRegistry(String performerType) {
        this.performerType = performerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(performerType, type);
    }

    @Override
    public String provideType() {
        return performerType;
    }

    public String getPerformerType() {
        return performerType;
    }

    public void setPerformerType(String performerType) {
        this.performerType = performerType;
    }

    @Override
    public String toString() {
        return "AbstractPerformerRegistry{" +
                "performerType='" + performerType + '\'' +
                '}';
    }
}
