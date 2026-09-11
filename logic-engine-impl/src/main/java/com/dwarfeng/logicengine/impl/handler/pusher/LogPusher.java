package com.dwarfeng.logicengine.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.logicengine.sdk.bean.entity.FastJsonSection;
import com.dwarfeng.logicengine.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.logicengine.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 将信息输出至日志的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class LogPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "log";

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPusher.class);

    private static final String LEVEL_TRACE = "TRACE";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";

    @Value("${com.dwarfeng.logicengine.pusher.log.log_level}")
    private String logLevel;

    public LogPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void taskFinished(Section section) throws HandlerException {
        String title = "推送任务完成消息:";
        String message = String.format(
                "部件:\n%s",
                JSON.toJSONString(FastJsonSection.of(section), true)
        );
        logData(title, message);
    }

    @Override
    public void taskFailed(Section section) throws HandlerException {
        String title = "推送任务失败消息:";
        String message = String.format(
                "部件:\n%s",
                JSON.toJSONString(FastJsonSection.of(section), true)
        );
        logData(title, message);
    }

    @Override
    public void taskExpired(Section section) throws HandlerException {
        String title = "推送任务过期消息:";
        String message = String.format(
                "部件:\n%s",
                JSON.toJSONString(FastJsonSection.of(section), true)
        );
        logData(title, message);
    }

    @Override
    public void taskDied(Section section) throws HandlerException {
        String title = "推送任务死亡消息:";
        String message = String.format(
                "部件:\n%s",
                JSON.toJSONString(FastJsonSection.of(section), true)
        );
        logData(title, message);
    }

    @Override
    public void superviseReset() throws HandlerException {
        logData("主管功能重置:", StringUtils.EMPTY);
    }

    @Override
    public void jobReset() throws HandlerException {
        logData("作业功能重置:", StringUtils.EMPTY);
    }

    @Override
    public void purgeFinished(PurgeFinishedResult result) throws HandlerException {
        logData("清除完成事件:", Objects.toString(result));
    }

    @Override
    public void purgeFailed() throws HandlerException {
        logData("清除失败事件:", StringUtils.EMPTY);
    }

    private void logData(String title, String message) throws HandlerException {
        String currentLogLevel = StringUtils.upperCase(logLevel);
        String logMessage = title;
        if (StringUtils.isNotEmpty(message)) {
            logMessage = String.join("\n", title, message);
        }
        logString(logMessage, currentLogLevel);
    }

    private void logString(String logMessage, String currentLogLevel) throws HandlerException {
        switch (currentLogLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(logMessage);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(logMessage);
                return;
            case LEVEL_INFO:
                LOGGER.info(logMessage);
                return;
            case LEVEL_WARN:
                LOGGER.warn(logMessage);
                return;
            case LEVEL_ERROR:
                LOGGER.error(logMessage);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + currentLogLevel);
        }
    }

    @Override
    public String toString() {
        return "LogPusher{" +
                "logLevel='" + logLevel + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
