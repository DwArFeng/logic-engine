package com.dwarfeng.logicengine.node.all.he.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.logicengine.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonSection.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonState.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonDriverInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonDriverSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonGuarderInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonGuarderSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPerformerInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPerformerSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTaskEvent.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTaskVariable.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
