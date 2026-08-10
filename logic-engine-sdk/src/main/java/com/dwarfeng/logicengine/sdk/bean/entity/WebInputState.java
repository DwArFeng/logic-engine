package com.dwarfeng.logicengine.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.logicengine.sdk.bean.key.WebInputStateKey;
import com.dwarfeng.logicengine.sdk.util.Constraints;
import com.dwarfeng.logicengine.sdk.util.ValidStateType;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 状态。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputState implements Bean {

    private static final long serialVersionUID = -1399265142124800186L;

    public static State toStackBean(WebInputState webInputState) {
        if (Objects.isNull(webInputState)) {
            return null;
        } else {
            return new State(
                    WebInputStateKey.toStackBean(webInputState.getKey()),
                    webInputState.getName(),
                    webInputState.getType(),
                    webInputState.getFirstSpinDelay(),
                    webInputState.getSpinInterval(),
                    webInputState.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputStateKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "type")
    @ValidStateType
    private int type;

    @JSONField(name = "first_spin_delay")
    @PositiveOrZero
    private long firstSpinDelay;

    @JSONField(name = "spin_interval")
    @PositiveOrZero
    private long spinInterval;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputState() {
    }

    public WebInputStateKey getKey() {
        return key;
    }

    public void setKey(WebInputStateKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFirstSpinDelay() {
        return firstSpinDelay;
    }

    public void setFirstSpinDelay(long firstSpinDelay) {
        this.firstSpinDelay = firstSpinDelay;
    }

    public long getSpinInterval() {
        return spinInterval;
    }

    public void setSpinInterval(long spinInterval) {
        this.spinInterval = spinInterval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputState{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", firstSpinDelay=" + firstSpinDelay +
                ", spinInterval=" + spinInterval +
                ", remark='" + remark + '\'' +
                '}';
    }
}
