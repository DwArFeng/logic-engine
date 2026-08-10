package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 守卫器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputGuarderSupport implements Bean {

    private static final long serialVersionUID = -6016158063984515745L;

    public static GuarderSupport toStackBean(WebInputGuarderSupport webInputGuarderSupport) {
        if (Objects.isNull(webInputGuarderSupport)) {
            return null;
        } else {
            return new GuarderSupport(
                    WebInputStringIdKey.toStackBean(webInputGuarderSupport.getKey()),
                    webInputGuarderSupport.getLabel(),
                    webInputGuarderSupport.getDescription(),
                    webInputGuarderSupport.getExampleParam()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputStringIdKey key;

    @JSONField(name = "label")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_LABEL)
    private String label;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "example_param")
    private String exampleParam;

    public WebInputGuarderSupport() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputGuarderSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
