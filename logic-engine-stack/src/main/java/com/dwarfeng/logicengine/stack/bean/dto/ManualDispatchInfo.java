package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 手动调度信息。
 *
 * @author DwArFeng
 * @since 1.1.2
 */
public class ManualDispatchInfo implements Dto {

    private static final long serialVersionUID = 2424690968557301643L;

    /**
     * 部件主键。
     */
    private LongIdKey sectionKey;

    public ManualDispatchInfo() {
    }

    public ManualDispatchInfo(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    @Override
    public String toString() {
        return "ManualDispatchInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
