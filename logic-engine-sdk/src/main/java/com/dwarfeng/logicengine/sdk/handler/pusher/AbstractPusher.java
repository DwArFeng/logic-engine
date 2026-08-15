package com.dwarfeng.logicengine.sdk.handler.pusher;

import com.dwarfeng.logicengine.sdk.handler.Pusher;

import java.util.Objects;

/**
 * 抽象推送器。
 *
 * <p>
 * 推送器的抽象实现，统一维护推送器类型并提供类型匹配逻辑。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractPusher implements Pusher {

    protected String pusherType;

    public AbstractPusher() {
    }

    public AbstractPusher(String pusherType) {
        this.pusherType = pusherType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(pusherType, type);
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }

    @Override
    public String toString() {
        return "AbstractPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
