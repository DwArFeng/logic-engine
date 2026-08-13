package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.handler.GuarderMaker;
import com.dwarfeng.logicengine.stack.exception.GuarderException;
import com.dwarfeng.logicengine.stack.exception.UnsupportedGuarderTypeException;
import com.dwarfeng.logicengine.stack.handler.Guarder;
import com.dwarfeng.logicengine.stack.handler.GuarderHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuarderHandlerImpl implements GuarderHandler {

    private final List<GuarderMaker> guarderMakers;

    public GuarderHandlerImpl(List<GuarderMaker> guarderMakers) {
        this.guarderMakers = guarderMakers;
    }

    @Override
    public Guarder make(String type, String param) throws GuarderException {
        try {
            // 从注册的构造器中选择支持指定类型的实现。
            for (GuarderMaker maker : guarderMakers) {
                if (maker.supportType(type)) {
                    return maker.makeGuarder(type, param);
                }
            }
            throw new UnsupportedGuarderTypeException(type);
        } catch (GuarderException e) {
            throw e;
        } catch (Exception e) {
            throw new GuarderException(e);
        }
    }
}
