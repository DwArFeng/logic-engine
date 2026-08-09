package com.dwarfeng.logicengine.node.all.he.configuration;

import com.dwarfeng.logicengine.impl.service.operation.SectionCrudOperation;
import com.dwarfeng.logicengine.impl.service.operation.StateCrudOperation;
import com.dwarfeng.logicengine.impl.service.operation.TaskCrudOperation;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.cache.*;
import com.dwarfeng.logicengine.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

    private final SectionCrudOperation sectionCrudOperation;
    private final SectionDao sectionDao;

    private final StateCrudOperation stateCrudOperation;
    private final StateDao stateDao;

    private final DriverInfoDao driverInfoDao;
    private final DriverInfoCache driverInfoCache;

    private final DriverSupportDao driverSupportDao;
    private final DriverSupportCache driverSupportCache;

    private final GuarderInfoDao guarderInfoDao;
    private final GuarderInfoCache guarderInfoCache;

    private final GuarderSupportDao guarderSupportDao;
    private final GuarderSupportCache guarderSupportCache;

    private final PerformerInfoDao performerInfoDao;
    private final PerformerInfoCache performerInfoCache;

    private final PerformerSupportDao performerSupportDao;
    private final PerformerSupportCache performerSupportCache;

    private final TaskCrudOperation taskCrudOperation;
    private final TaskDao taskDao;

    private final TaskEventDao taskEventDao;
    private final TaskEventCache taskEventCache;

    private final TaskVariableDao taskVariableDao;
    private final TaskVariableCache taskVariableCache;

    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.driver_info}")
    private long driverInfoTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.driver_support}")
    private long driverSupportTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.guarder_info}")
    private long guarderInfoTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.guarder_support}")
    private long guarderSupportTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.performer_info}")
    private long performerInfoTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.performer_support}")
    private long performerSupportTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.task_event}")
    private long taskEventTimeout;
    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.task_variable}")
    private long taskVariableTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            SectionCrudOperation sectionCrudOperation,
            SectionDao sectionDao,
            StateCrudOperation stateCrudOperation,
            StateDao stateDao,
            DriverInfoDao driverInfoDao,
            DriverInfoCache driverInfoCache,
            DriverSupportDao driverSupportDao,
            DriverSupportCache driverSupportCache,
            GuarderInfoDao guarderInfoDao,
            GuarderInfoCache guarderInfoCache,
            GuarderSupportDao guarderSupportDao,
            GuarderSupportCache guarderSupportCache,
            PerformerInfoDao performerInfoDao,
            PerformerInfoCache performerInfoCache,
            PerformerSupportDao performerSupportDao,
            PerformerSupportCache performerSupportCache,
            TaskCrudOperation taskCrudOperation,
            TaskDao taskDao,
            TaskEventDao taskEventDao,
            TaskEventCache taskEventCache,
            TaskVariableDao taskVariableDao,
            TaskVariableCache taskVariableCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.sectionCrudOperation = sectionCrudOperation;
        this.sectionDao = sectionDao;
        this.stateCrudOperation = stateCrudOperation;
        this.stateDao = stateDao;
        this.driverInfoDao = driverInfoDao;
        this.driverInfoCache = driverInfoCache;
        this.driverSupportDao = driverSupportDao;
        this.driverSupportCache = driverSupportCache;
        this.guarderInfoDao = guarderInfoDao;
        this.guarderInfoCache = guarderInfoCache;
        this.guarderSupportDao = guarderSupportDao;
        this.guarderSupportCache = guarderSupportCache;
        this.performerInfoDao = performerInfoDao;
        this.performerInfoCache = performerInfoCache;
        this.performerSupportDao = performerSupportDao;
        this.performerSupportCache = performerSupportCache;
        this.taskCrudOperation = taskCrudOperation;
        this.taskDao = taskDao;
        this.taskEventDao = taskEventDao;
        this.taskEventCache = taskEventCache;
        this.taskVariableDao = taskVariableDao;
        this.taskVariableCache = taskVariableCache;
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Section> sectionCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                sectionCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Section> sectionDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                sectionDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Section> sectionDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                sectionDao
        );
    }

    @Bean
    public CustomBatchCrudService<StateKey, State> stateCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                stateCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<State> stateDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                stateDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<State> stateDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                stateDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, DriverInfo> driverInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao,
                driverInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                driverInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverInfo> driverInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverInfo> driverInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, DriverSupport> driverSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao,
                driverSupportCache,
                new ExceptionKeyGenerator<>(),
                driverSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverSupport> driverSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverSupport> driverSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, GuarderInfo> guarderInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderInfoDao,
                guarderInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                guarderInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<GuarderInfo> guarderInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<GuarderInfo> guarderInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, GuarderSupport> guarderSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderSupportDao,
                guarderSupportCache,
                new ExceptionKeyGenerator<>(),
                guarderSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<GuarderSupport> guarderSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<GuarderSupport> guarderSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                guarderSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, PerformerInfo> performerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerInfoDao,
                performerInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                performerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<PerformerInfo> performerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<PerformerInfo> performerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, PerformerSupport> performerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerSupportDao,
                performerSupportCache,
                new ExceptionKeyGenerator<>(),
                performerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<PerformerSupport> performerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<PerformerSupport> performerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                performerSupportDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Task> taskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Task> taskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Task> taskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, TaskEvent> taskEventGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao,
                taskEventCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                taskEventTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TaskEvent> taskEventDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TaskEvent> taskEventDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao
        );
    }

    @Bean
    public GeneralBatchCrudService<TaskVariableKey, TaskVariable> taskVariableGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskVariableDao,
                taskVariableCache,
                new ExceptionKeyGenerator<>(),
                taskVariableTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TaskVariable> taskVariableDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskVariableDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TaskVariable> taskVariableDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskVariableDao
        );
    }
}
