package com.dwarfeng.logicengine.sdk.bean;

import com.dwarfeng.logicengine.sdk.bean.entity.*;
import com.dwarfeng.logicengine.sdk.bean.key.*;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // endregion

    // region LogicEngine Key

    FastJsonStateKey stateKeyToFastJson(StateKey stateKey);

    @InheritInverseConfiguration
    StateKey stateKeyFromFastJson(FastJsonStateKey fastJsonStateKey);

    JSFixedFastJsonStateKey stateKeyToJSFixedFastJson(StateKey stateKey);

    @InheritInverseConfiguration
    StateKey stateKeyFromJSFixedFastJson(JSFixedFastJsonStateKey jSFixedFastJsonStateKey);

    WebInputStateKey stateKeyToWebInput(StateKey stateKey);

    @InheritInverseConfiguration
    StateKey stateKeyFromWebInput(WebInputStateKey webInputStateKey);

    FastJsonTaskVariableKey taskVariableKeyToFastJson(TaskVariableKey taskVariableKey);

    @InheritInverseConfiguration
    TaskVariableKey taskVariableKeyFromFastJson(FastJsonTaskVariableKey fastJsonTaskVariableKey);

    JSFixedFastJsonTaskVariableKey taskVariableKeyToJSFixedFastJson(TaskVariableKey taskVariableKey);

    @InheritInverseConfiguration
    TaskVariableKey taskVariableKeyFromJSFixedFastJson(JSFixedFastJsonTaskVariableKey jSFixedFastJsonTaskVariableKey);

    WebInputTaskVariableKey taskVariableKeyToWebInput(TaskVariableKey taskVariableKey);

    @InheritInverseConfiguration
    TaskVariableKey taskVariableKeyFromWebInput(WebInputTaskVariableKey webInputTaskVariableKey);

    // endregion

    // region LogicEngine Entity

    FastJsonSection sectionToFastJson(Section section);

    @InheritInverseConfiguration
    Section sectionFromFastJson(FastJsonSection fastJsonSection);

    JSFixedFastJsonSection sectionToJSFixedFastJson(Section section);

    @InheritInverseConfiguration
    Section sectionFromJSFixedFastJson(JSFixedFastJsonSection jSFixedFastJsonSection);

    WebInputSection sectionToWebInput(Section section);

    @InheritInverseConfiguration
    Section sectionFromWebInput(WebInputSection webInputSection);

    FastJsonState stateToFastJson(State state);

    @InheritInverseConfiguration
    State stateFromFastJson(FastJsonState fastJsonState);

    JSFixedFastJsonState stateToJSFixedFastJson(State state);

    @InheritInverseConfiguration
    State stateFromJSFixedFastJson(JSFixedFastJsonState jSFixedFastJsonState);

    WebInputState stateToWebInput(State state);

    @InheritInverseConfiguration
    State stateFromWebInput(WebInputState webInputState);

    FastJsonDriverInfo driverInfoToFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromFastJson(FastJsonDriverInfo fastJsonDriverInfo);

    JSFixedFastJsonDriverInfo driverInfoToJSFixedFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromJSFixedFastJson(JSFixedFastJsonDriverInfo jSFixedFastJsonDriverInfo);

    WebInputDriverInfo driverInfoToWebInput(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromWebInput(WebInputDriverInfo webInputDriverInfo);

    FastJsonDriverSupport driverSupportToFastJson(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromFastJson(FastJsonDriverSupport fastJsonDriverSupport);

    WebInputDriverSupport driverSupportToWebInput(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromWebInput(WebInputDriverSupport webInputDriverSupport);

    FastJsonGuarderInfo guarderInfoToFastJson(GuarderInfo guarderInfo);

    @InheritInverseConfiguration
    GuarderInfo guarderInfoFromFastJson(FastJsonGuarderInfo fastJsonGuarderInfo);

    JSFixedFastJsonGuarderInfo guarderInfoToJSFixedFastJson(GuarderInfo guarderInfo);

    @InheritInverseConfiguration
    GuarderInfo guarderInfoFromJSFixedFastJson(JSFixedFastJsonGuarderInfo jSFixedFastJsonGuarderInfo);

    WebInputGuarderInfo guarderInfoToWebInput(GuarderInfo guarderInfo);

    @InheritInverseConfiguration
    GuarderInfo guarderInfoFromWebInput(WebInputGuarderInfo webInputGuarderInfo);

    FastJsonGuarderSupport guarderSupportToFastJson(GuarderSupport guarderSupport);

    @InheritInverseConfiguration
    GuarderSupport guarderSupportFromFastJson(FastJsonGuarderSupport fastJsonGuarderSupport);

    WebInputGuarderSupport guarderSupportToWebInput(GuarderSupport guarderSupport);

    @InheritInverseConfiguration
    GuarderSupport guarderSupportFromWebInput(WebInputGuarderSupport webInputGuarderSupport);

    FastJsonPerformerInfo performerInfoToFastJson(PerformerInfo performerInfo);

    @InheritInverseConfiguration
    PerformerInfo performerInfoFromFastJson(FastJsonPerformerInfo fastJsonPerformerInfo);

    JSFixedFastJsonPerformerInfo performerInfoToJSFixedFastJson(PerformerInfo performerInfo);

    @InheritInverseConfiguration
    PerformerInfo performerInfoFromJSFixedFastJson(JSFixedFastJsonPerformerInfo jSFixedFastJsonPerformerInfo);

    WebInputPerformerInfo performerInfoToWebInput(PerformerInfo performerInfo);

    @InheritInverseConfiguration
    PerformerInfo performerInfoFromWebInput(WebInputPerformerInfo webInputPerformerInfo);

    FastJsonPerformerSupport performerSupportToFastJson(PerformerSupport performerSupport);

    @InheritInverseConfiguration
    PerformerSupport performerSupportFromFastJson(FastJsonPerformerSupport fastJsonPerformerSupport);

    WebInputPerformerSupport performerSupportToWebInput(PerformerSupport performerSupport);

    @InheritInverseConfiguration
    PerformerSupport performerSupportFromWebInput(WebInputPerformerSupport webInputPerformerSupport);

    FastJsonTask taskToFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromFastJson(FastJsonTask fastJsonTask);

    JSFixedFastJsonTask taskToJSFixedFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromJSFixedFastJson(JSFixedFastJsonTask jSFixedFastJsonTask);

    WebInputTask taskToWebInput(Task task);

    @InheritInverseConfiguration
    Task taskFromWebInput(WebInputTask webInputTask);

    FastJsonTaskEvent taskEventToFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromFastJson(FastJsonTaskEvent fastJsonTaskEvent);

    JSFixedFastJsonTaskEvent taskEventToJSFixedFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromJSFixedFastJson(JSFixedFastJsonTaskEvent jSFixedFastJsonTaskEvent);

    WebInputTaskEvent taskEventToWebInput(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromWebInput(WebInputTaskEvent webInputTaskEvent);

    FastJsonTaskVariable taskVariableToFastJson(TaskVariable taskVariable);

    @InheritInverseConfiguration
    TaskVariable taskVariableFromFastJson(FastJsonTaskVariable fastJsonTaskVariable);

    JSFixedFastJsonTaskVariable taskVariableToJSFixedFastJson(TaskVariable taskVariable);

    @InheritInverseConfiguration
    TaskVariable taskVariableFromJSFixedFastJson(JSFixedFastJsonTaskVariable jSFixedFastJsonTaskVariable);

    WebInputTaskVariable taskVariableToWebInput(TaskVariable taskVariable);

    @InheritInverseConfiguration
    TaskVariable taskVariableFromWebInput(WebInputTaskVariable webInputTaskVariable);

    // endregion
}
