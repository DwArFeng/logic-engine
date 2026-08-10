package com.dwarfeng.logicengine.sdk.bean.key.formatter;

import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * TaskVariableKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskVariableStringKeyFormatter implements StringKeyFormatter<TaskVariableKey> {

    private String prefix;

    public TaskVariableStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(TaskVariableKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getTaskLongId() + "_" + key.getVariableStringId();
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
        return "TaskVariableStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
