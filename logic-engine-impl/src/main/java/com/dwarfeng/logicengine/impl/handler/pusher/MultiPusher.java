package com.dwarfeng.logicengine.impl.handler.pusher;

import com.dwarfeng.logicengine.sdk.handler.Pusher;
import com.dwarfeng.logicengine.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 同时将消息推送给所有代理的多重推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "multi";

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPusher.class);

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.logicengine.pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher(List<Pusher> pushers) {
        super(PUSHER_TYPE);
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        StringTokenizer stringTokenizer = new StringTokenizer(delegateTypes, ",");
        while (stringTokenizer.hasMoreTokens()) {
            String delegateType = stringTokenizer.nextToken();
            delegates.add(
                    pushers.stream().filter(item -> item.supportType(delegateType)).findAny().orElseThrow(
                            () -> new HandlerException("未知的推送器类型: " + delegateType)
                    )
            );
        }
    }

    @Override
    public void taskFinished(Section section) {
        for (Pusher delegate : delegates) {
            try {
                delegate.taskFinished(section);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void taskFailed(Section section) {
        for (Pusher delegate : delegates) {
            try {
                delegate.taskFailed(section);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void taskExpired(Section section) {
        for (Pusher delegate : delegates) {
            try {
                delegate.taskExpired(section);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void taskDied(Section section) {
        for (Pusher delegate : delegates) {
            try {
                delegate.taskDied(section);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void superviseReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.superviseReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void jobReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.jobReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void purgeFinished(PurgeFinishedResult result) {
        for (Pusher delegate : delegates) {
            try {
                delegate.purgeFinished(result);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void purgeFailed() {
        for (Pusher delegate : delegates) {
            try {
                delegate.purgeFailed();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public String toString() {
        return "MultiPusher{" +
                "delegateTypes='" + delegateTypes + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
