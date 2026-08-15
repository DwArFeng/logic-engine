package com.dwarfeng.logicengine.impl.handler.pusher;

import com.dwarfeng.logicengine.sdk.handler.pusher.PusherAdapter;
import org.springframework.stereotype.Component;

/**
 * 简单丢弃所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainPusher extends PusherAdapter {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public String toString() {
        return "DrainPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
