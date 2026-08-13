package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 作业创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JobCreateInfo implements Dto {

    private static final long serialVersionUID = 1263461420685088039L;
    /**
     * 部件主键。
     */
    private LongIdKey sectionKey;

    public JobCreateInfo() {
    }

    public JobCreateInfo(LongIdKey sectionKey) {
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
        return "JobCreateInfo{" +
                "sectionKey=" + sectionKey +
                '}';
    }
}
