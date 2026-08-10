package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 驱动器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DriverSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = 5552615555562984928L;

    private StringIdKey key;

    /**
     * 驱动器类型的显示名称。
     */
    private String label;

    /**
     * 驱动器类型说明。
     */
    private String description;

    /**
     * 驱动器参数示例。
     */
    private String exampleParam;

    public DriverSupport() {
    }

    public DriverSupport(StringIdKey key, String label, String description, String exampleParam) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleParam() {
        return exampleParam;
    }

    public void setExampleParam(String exampleParam) {
        this.exampleParam = exampleParam;
    }

    @Override
    public String toString() {
        return "DriverSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
