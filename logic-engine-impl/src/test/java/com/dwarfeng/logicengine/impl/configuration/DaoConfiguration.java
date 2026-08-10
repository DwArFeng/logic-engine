package com.dwarfeng.logicengine.impl.configuration;

import com.dwarfeng.logicengine.impl.bean.BeanMapper;
import com.dwarfeng.logicengine.impl.bean.entity.*;
import com.dwarfeng.logicengine.impl.bean.key.HibernateStateKey;
import com.dwarfeng.logicengine.impl.bean.key.HibernateTaskVariableKey;
import com.dwarfeng.logicengine.impl.dao.preset.*;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate template;

    private final SectionPresetCriteriaMaker sectionPresetCriteriaMaker;
    private final StatePresetCriteriaMaker statePresetCriteriaMaker;
    private final DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker;
    private final DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker;
    private final GuarderInfoPresetCriteriaMaker guarderInfoPresetCriteriaMaker;
    private final GuarderSupportPresetCriteriaMaker guarderSupportPresetCriteriaMaker;
    private final PerformerInfoPresetCriteriaMaker performerInfoPresetCriteriaMaker;
    private final PerformerSupportPresetCriteriaMaker performerSupportPresetCriteriaMaker;
    private final TaskPresetCriteriaMaker taskPresetCriteriaMaker;
    private final TaskEventPresetCriteriaMaker taskEventPresetCriteriaMaker;
    private final TaskVariablePresetCriteriaMaker taskVariablePresetCriteriaMaker;

    @Value("${com.dwarfeng.logicengine.hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate template,
            SectionPresetCriteriaMaker sectionPresetCriteriaMaker,
            StatePresetCriteriaMaker statePresetCriteriaMaker,
            DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker,
            DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker,
            GuarderInfoPresetCriteriaMaker guarderInfoPresetCriteriaMaker,
            GuarderSupportPresetCriteriaMaker guarderSupportPresetCriteriaMaker,
            PerformerInfoPresetCriteriaMaker performerInfoPresetCriteriaMaker,
            PerformerSupportPresetCriteriaMaker performerSupportPresetCriteriaMaker,
            TaskPresetCriteriaMaker taskPresetCriteriaMaker,
            TaskEventPresetCriteriaMaker taskEventPresetCriteriaMaker,
            TaskVariablePresetCriteriaMaker taskVariablePresetCriteriaMaker
    ) {
        this.template = template;
        this.sectionPresetCriteriaMaker = sectionPresetCriteriaMaker;
        this.statePresetCriteriaMaker = statePresetCriteriaMaker;
        this.driverInfoPresetCriteriaMaker = driverInfoPresetCriteriaMaker;
        this.driverSupportPresetCriteriaMaker = driverSupportPresetCriteriaMaker;
        this.guarderInfoPresetCriteriaMaker = guarderInfoPresetCriteriaMaker;
        this.guarderSupportPresetCriteriaMaker = guarderSupportPresetCriteriaMaker;
        this.performerInfoPresetCriteriaMaker = performerInfoPresetCriteriaMaker;
        this.performerSupportPresetCriteriaMaker = performerSupportPresetCriteriaMaker;
        this.taskPresetCriteriaMaker = taskPresetCriteriaMaker;
        this.taskEventPresetCriteriaMaker = taskEventPresetCriteriaMaker;
        this.taskVariablePresetCriteriaMaker = taskVariablePresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Section, HibernateSection>
    sectionHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Section.class, HibernateSection.class, BeanMapper.class),
                HibernateSection.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Section, HibernateSection> sectionHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Section.class, HibernateSection.class, BeanMapper.class),
                HibernateSection.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Section, HibernateSection> sectionHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Section.class, HibernateSection.class, BeanMapper.class),
                HibernateSection.class,
                sectionPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StateKey, HibernateStateKey, State, HibernateState> stateHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StateKey.class, HibernateStateKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(State.class, HibernateState.class, BeanMapper.class),
                HibernateState.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<State, HibernateState> stateHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(State.class, HibernateState.class, BeanMapper.class),
                HibernateState.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<State, HibernateState> stateHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(State.class, HibernateState.class, BeanMapper.class),
                HibernateState.class,
                statePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, DriverInfo, HibernateDriverInfo>
    driverInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, BeanMapper.class),
                HibernateDriverInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, BeanMapper.class),
                HibernateDriverInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, BeanMapper.class),
                HibernateDriverInfo.class,
                driverInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, DriverSupport, HibernateDriverSupport>
    driverSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, BeanMapper.class),
                HibernateDriverSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverSupport, HibernateDriverSupport>
    driverSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, BeanMapper.class),
                HibernateDriverSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverSupport, HibernateDriverSupport> driverSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, BeanMapper.class),
                HibernateDriverSupport.class,
                driverSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, GuarderInfo, HibernateGuarderInfo>
    guarderInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(GuarderInfo.class, HibernateGuarderInfo.class, BeanMapper.class),
                HibernateGuarderInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<GuarderInfo, HibernateGuarderInfo> guarderInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(GuarderInfo.class, HibernateGuarderInfo.class, BeanMapper.class),
                HibernateGuarderInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<GuarderInfo, HibernateGuarderInfo> guarderInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(GuarderInfo.class, HibernateGuarderInfo.class, BeanMapper.class),
                HibernateGuarderInfo.class,
                guarderInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, GuarderSupport, HibernateGuarderSupport>
    guarderSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(GuarderSupport.class, HibernateGuarderSupport.class, BeanMapper.class),
                HibernateGuarderSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<GuarderSupport, HibernateGuarderSupport> guarderSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(GuarderSupport.class, HibernateGuarderSupport.class, BeanMapper.class),
                HibernateGuarderSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<GuarderSupport, HibernateGuarderSupport> guarderSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(GuarderSupport.class, HibernateGuarderSupport.class, BeanMapper.class),
                HibernateGuarderSupport.class,
                guarderSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PerformerInfo, HibernatePerformerInfo>
    performerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(PerformerInfo.class, HibernatePerformerInfo.class, BeanMapper.class),
                HibernatePerformerInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<PerformerInfo, HibernatePerformerInfo> performerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(PerformerInfo.class, HibernatePerformerInfo.class, BeanMapper.class),
                HibernatePerformerInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PerformerInfo, HibernatePerformerInfo> performerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(PerformerInfo.class, HibernatePerformerInfo.class, BeanMapper.class),
                HibernatePerformerInfo.class,
                performerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, PerformerSupport, HibernatePerformerSupport>
    performerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        PerformerSupport.class, HibernatePerformerSupport.class, BeanMapper.class
                ),
                HibernatePerformerSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<PerformerSupport, HibernatePerformerSupport>
    performerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        PerformerSupport.class, HibernatePerformerSupport.class, BeanMapper.class
                ),
                HibernatePerformerSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PerformerSupport, HibernatePerformerSupport>
    performerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        PerformerSupport.class, HibernatePerformerSupport.class, BeanMapper.class
                ),
                HibernatePerformerSupport.class,
                performerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Task, HibernateTask> taskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, BeanMapper.class),
                HibernateTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Task, HibernateTask> taskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, BeanMapper.class),
                HibernateTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Task, HibernateTask> taskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, BeanMapper.class),
                HibernateTask.class,
                taskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TaskEvent, HibernateTaskEvent>
    taskEventHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, BeanMapper.class),
                HibernateTaskEvent.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<TaskEvent, HibernateTaskEvent> taskEventHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, BeanMapper.class),
                HibernateTaskEvent.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TaskEvent, HibernateTaskEvent> taskEventHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, BeanMapper.class),
                HibernateTaskEvent.class,
                taskEventPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<TaskVariableKey, HibernateTaskVariableKey, TaskVariable, HibernateTaskVariable>
    taskVariableHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(TaskVariableKey.class, HibernateTaskVariableKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(TaskVariable.class, HibernateTaskVariable.class, BeanMapper.class),
                HibernateTaskVariable.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<TaskVariable, HibernateTaskVariable> taskVariableHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TaskVariable.class, HibernateTaskVariable.class, BeanMapper.class),
                HibernateTaskVariable.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TaskVariable, HibernateTaskVariable> taskVariableHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TaskVariable.class, HibernateTaskVariable.class, BeanMapper.class),
                HibernateTaskVariable.class,
                taskVariablePresetCriteriaMaker
        );
    }
}
