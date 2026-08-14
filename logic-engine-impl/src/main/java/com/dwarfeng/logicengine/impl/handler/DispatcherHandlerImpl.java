package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.stack.handler.Dispatcher;
import com.dwarfeng.logicengine.stack.handler.DispatcherHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DispatcherHandlerImpl implements DispatcherHandler {

    private final List<Dispatcher> dispatchers;

    @Value("${com.dwarfeng.logicengine.dispatcher.type}")
    private String dispatcherType;

    private Dispatcher dispatcher;

    public DispatcherHandlerImpl(List<Dispatcher> dispatchers) {
        this.dispatchers = Optional.ofNullable(dispatchers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        dispatcher = dispatchers.stream().filter(item -> item.supportType(dispatcherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的调度器类型: " + dispatcherType)
        );
    }

    @Override
    public Dispatcher current() {
        return dispatcher;
    }

    @Override
    public List<Dispatcher> all() {
        return dispatchers;
    }
}
