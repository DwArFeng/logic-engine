package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.handler.PerformerMaker;
import com.dwarfeng.logicengine.stack.exception.PerformerException;
import com.dwarfeng.logicengine.stack.exception.UnsupportedPerformerTypeException;
import com.dwarfeng.logicengine.stack.handler.Performer;
import com.dwarfeng.logicengine.stack.handler.PerformerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PerformerHandlerImpl implements PerformerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformerHandlerImpl.class);

    private final List<PerformerMaker> performerMakers;

    public PerformerHandlerImpl(List<PerformerMaker> performerMakers) {
        this.performerMakers = Optional.ofNullable(performerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Performer make(String type, String param) throws PerformerException {
        try {
            // 生成执行器。
            LOGGER.debug("通过执行器信息构建新的执行器...");
            PerformerMaker performerMaker = performerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedPerformerTypeException(type));
            Performer performer = performerMaker.makePerformer(type, param);
            LOGGER.debug("执行器构建成功!");
            LOGGER.debug("执行器: {}", performer);
            return performer;
        } catch (PerformerException e) {
            throw e;
        } catch (Exception e) {
            throw new PerformerException(e);
        }
    }
}
