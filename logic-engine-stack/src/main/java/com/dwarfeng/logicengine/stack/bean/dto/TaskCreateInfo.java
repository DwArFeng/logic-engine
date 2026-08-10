package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskCreateInfo implements Dto {

    private static final long serialVersionUID = 4768834050331423342L;

    /**
     * 部件主键。
     */
    private LongIdKey sectionKey;

    public TaskCreateInfo() {
    }

    public TaskCreateInfo(LongIdKey sectionKey) {
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
        return "TaskCreateInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
