package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 驱动器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputDriverSupport implements Bean {

    private static final long serialVersionUID = 2002725842604172172L;

    public static DriverSupport toStackBean(WebInputDriverSupport webInputDriverSupport) {
        if (Objects.isNull(webInputDriverSupport)) {
            return null;
        } else {
            return new DriverSupport(
                    WebInputStringIdKey.toStackBean(webInputDriverSupport.getKey()),
                    webInputDriverSupport.getLabel(),
                    webInputDriverSupport.getDescription(),
                    webInputDriverSupport.getExampleParam()
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

    public WebInputDriverSupport() {
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
        return "WebInputDriverSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
