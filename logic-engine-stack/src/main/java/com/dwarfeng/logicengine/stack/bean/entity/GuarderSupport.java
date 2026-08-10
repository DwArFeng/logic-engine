package com.dwarfeng.logicengine.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 守卫器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GuarderSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = 498610929965144816L;

    private StringIdKey key;

    /**
     * 守卫器类型的显示名称。
     */
    private String label;

    /**
     * 守卫器类型说明。
     */
    private String description;

    /**
     * 守卫器参数示例。
     */
    private String exampleParam;

    public GuarderSupport() {
    }

    public GuarderSupport(StringIdKey key, String label, String description, String exampleParam) {
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
        return "GuarderSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
