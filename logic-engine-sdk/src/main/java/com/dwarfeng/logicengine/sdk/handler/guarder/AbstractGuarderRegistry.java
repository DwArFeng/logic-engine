package com.dwarfeng.logicengine.sdk.handler.guarder;

import com.dwarfeng.logicengine.sdk.handler.GuarderMaker;
import com.dwarfeng.logicengine.sdk.handler.GuarderSupporter;

import java.util.Objects;

/**
 * 抽象守卫器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractGuarderRegistry implements GuarderMaker, GuarderSupporter {

    protected String guarderType;

    public AbstractGuarderRegistry() {
    }

    public AbstractGuarderRegistry(String guarderType) {
        this.guarderType = guarderType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(guarderType, type);
    }

    @Override
    public String provideType() {
        return guarderType;
    }

    public String getGuarderType() {
        return guarderType;
    }

    public void setGuarderType(String guarderType) {
        this.guarderType = guarderType;
    }

    @Override
    public String toString() {
        return "AbstractGuarderRegistry{" +
                "guarderType='" + guarderType + '\'' +
                '}';
    }
}
