package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.handler.Pusher;
import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 推送处理器实现。
 *
 * <p>
 * 该实现根据配置选择一个推送器，并将所有推送事件委托给选中的推送器。推送器本身可以通过可选配置包进行替换，
 * 核心机制不需要感知具体协议。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.logicengine.pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        pusher = pushers.stream().filter(item -> item.supportType(pusherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的推送器类型: " + pusherType)
        );
    }

    @Override
    public void taskFinished(Section section) throws HandlerException {
        pusher.taskFinished(section);
    }

    @Override
    public void taskFailed(Section section) throws HandlerException {
        pusher.taskFailed(section);
    }

    @Override
    public void taskExpired(Section section) throws HandlerException {
        pusher.taskExpired(section);
    }

    @Override
    public void taskDied(Section section) throws HandlerException {
        pusher.taskDied(section);
    }

    @Override
    public void superviseReset() throws HandlerException {
        pusher.superviseReset();
    }

    @Override
    public void jobReset() throws HandlerException {
        pusher.jobReset();
    }

    @Override
    public void purgeFinished(PurgeFinishedResult result) throws HandlerException {
        pusher.purgeFinished(result);
    }

    @Override
    public void purgeFailed() throws HandlerException {
        pusher.purgeFailed();
    }

    @Override
    public String toString() {
        return "PushHandlerImpl{" +
                "pushers=" + pushers +
                ", pusherType='" + pusherType + '\'' +
                ", pusher=" + pusher +
                '}';
    }
}
