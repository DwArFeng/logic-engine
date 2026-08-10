package com.dwarfeng.logicengine.sdk.bean.key.formatter;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * StateKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StateStringKeyFormatter implements StringKeyFormatter<StateKey> {

    private String prefix;

    public StateStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(StateKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getSectionLongId() + "_" + key.getStateId();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "StateStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
