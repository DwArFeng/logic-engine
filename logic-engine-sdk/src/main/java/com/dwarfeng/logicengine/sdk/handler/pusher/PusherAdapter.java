package com.dwarfeng.logicengine.sdk.handler.pusher;

import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 推送器适配器。
 *
 * <p>
 * 该类对所有事件推送方法提供空实现。插件实现推送器时建议继承该类，只重写真正需要处理的事件；当推送接口增加新事件时，
 * 旧插件仍可保持兼容。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class PusherAdapter extends AbstractPusher {

    public PusherAdapter() {
        super();
    }

    public PusherAdapter(String pusherType) {
        super(pusherType);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void taskFinished(Section section) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void taskFailed(Section section) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void taskExpired(Section section) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void taskDied(Section section) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void superviseReset() throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void jobReset() throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void purgeFinished(PurgeFinishedResult result) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void purgeFailed() throws HandlerException {
    }

    @Override
    public String toString() {
        return "PusherAdapter{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
