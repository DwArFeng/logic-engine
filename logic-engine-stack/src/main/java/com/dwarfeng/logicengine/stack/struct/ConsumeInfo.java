package com.dwarfeng.logicengine.stack.struct;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 消费信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ConsumeInfo {

    private final LongIdKey sectionKey;

    public ConsumeInfo(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    @Override
    public String toString() {
        return "ConsumeInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
