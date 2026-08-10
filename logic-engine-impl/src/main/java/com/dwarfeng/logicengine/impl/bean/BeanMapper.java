package com.dwarfeng.logicengine.impl.bean;

import com.dwarfeng.logicengine.impl.bean.entity.*;
import com.dwarfeng.logicengine.impl.bean.key.HibernateStateKey;
import com.dwarfeng.logicengine.impl.bean.key.HibernateTaskVariableKey;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>impl</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // endregion

    // region LogicEngine Key

    HibernateStateKey stateKeyToHibernate(StateKey stateKey);

    @InheritInverseConfiguration
    StateKey stateKeyFromHibernate(HibernateStateKey hibernateStateKey);

    HibernateTaskVariableKey taskVariableKeyToHibernate(TaskVariableKey taskVariableKey);

    @InheritInverseConfiguration
    TaskVariableKey taskVariableKeyFromHibernate(HibernateTaskVariableKey hibernateTaskVariableKey);

    // endregion

    // region LogicEngine Entity

    @Mapping(target = "taskSet", ignore = true)
    @Mapping(target = "stateSet", ignore = true)
    @Mapping(target = "performerInfoSet", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "guarderInfoSet", ignore = true)
    @Mapping(target = "driverInfoSet", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateSection sectionToHibernate(Section section);

    @InheritInverseConfiguration
    Section sectionFromHibernate(HibernateSection hibernateSection);

    @Mapping(target = "targetPerformerInfoSet", ignore = true)
    @Mapping(target = "targetGuarderInfoSet", ignore = true)
    @Mapping(target = "stateId", ignore = true)
    @Mapping(target = "sectionLongId", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "anchorPerformerInfoSet", ignore = true)
    @Mapping(target = "anchorGuarderInfoSet", ignore = true)
    HibernateState stateToHibernate(State state);

    @InheritInverseConfiguration
    State stateFromHibernate(HibernateState hibernateState);

    @Mapping(target = "sectionLongId", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateDriverInfo driverInfoToHibernate(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromHibernate(HibernateDriverInfo hibernateDriverInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateDriverSupport driverSupportToHibernate(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromHibernate(HibernateDriverSupport hibernateDriverSupport);

    @Mapping(target = "targetStateStateId", ignore = true)
    @Mapping(target = "targetStateSectionLongId", ignore = true)
    @Mapping(target = "targetState", ignore = true)
    @Mapping(target = "sectionLongId", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "anchorStateStateId", ignore = true)
    @Mapping(target = "anchorStateSectionLongId", ignore = true)
    @Mapping(target = "anchorState", ignore = true)
    HibernateGuarderInfo guarderInfoToHibernate(GuarderInfo guarderInfo);

    @InheritInverseConfiguration
    GuarderInfo guarderInfoFromHibernate(HibernateGuarderInfo hibernateGuarderInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateGuarderSupport guarderSupportToHibernate(GuarderSupport guarderSupport);

    @InheritInverseConfiguration
    GuarderSupport guarderSupportFromHibernate(HibernateGuarderSupport hibernateGuarderSupport);

    @Mapping(target = "targetStateStateId", ignore = true)
    @Mapping(target = "targetStateSectionLongId", ignore = true)
    @Mapping(target = "targetState", ignore = true)
    @Mapping(target = "sectionLongId", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "anchorStateStateId", ignore = true)
    @Mapping(target = "anchorStateSectionLongId", ignore = true)
    @Mapping(target = "anchorState", ignore = true)
    HibernatePerformerInfo performerInfoToHibernate(PerformerInfo performerInfo);

    @InheritInverseConfiguration
    PerformerInfo performerInfoFromHibernate(HibernatePerformerInfo hibernatePerformerInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernatePerformerSupport performerSupportToHibernate(PerformerSupport performerSupport);

    @InheritInverseConfiguration
    PerformerSupport performerSupportFromHibernate(HibernatePerformerSupport hibernatePerformerSupport);

    @Mapping(target = "taskVariableSet", ignore = true)
    @Mapping(target = "taskEventSet", ignore = true)
    @Mapping(target = "sectionLongId", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "currentStateStateId", ignore = true)
    @Mapping(target = "currentStateSectionLongId", ignore = true)
    HibernateTask taskToHibernate(Task task);

    @InheritInverseConfiguration
    Task taskFromHibernate(HibernateTask hibernateTask);

    @Mapping(target = "taskLongId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateTaskEvent taskEventToHibernate(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromHibernate(HibernateTaskEvent hibernateTaskEvent);

    @Mapping(target = "variableStringId", ignore = true)
    @Mapping(target = "taskLongId", ignore = true)
    @Mapping(target = "task", ignore = true)
    HibernateTaskVariable taskVariableToHibernate(TaskVariable taskVariable);

    @InheritInverseConfiguration
    TaskVariable taskVariableFromHibernate(HibernateTaskVariable hibernateTaskVariable);

    // endregion
}
