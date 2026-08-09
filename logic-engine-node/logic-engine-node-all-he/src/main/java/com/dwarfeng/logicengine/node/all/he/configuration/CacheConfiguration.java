package com.dwarfeng.logicengine.node.all.he.configuration;

import com.dwarfeng.logicengine.sdk.bean.BeanMapper;
import com.dwarfeng.logicengine.sdk.bean.entity.*;
import com.dwarfeng.logicengine.sdk.bean.key.formatter.StateStringKeyFormatter;
import com.dwarfeng.logicengine.sdk.bean.key.formatter.TaskVariableStringKeyFormatter;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.section}")
    private String sectionPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.state}")
    private String statePrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.driver_info}")
    private String driverInfoPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.driver_support}")
    private String driverSupportPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.guarder_info}")
    private String guarderInfoPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.guarder_support}")
    private String guarderSupportPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.performer_info}")
    private String performerInfoPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.performer_support}")
    private String performerSupportPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.task}")
    private String taskPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.task_event}")
    private String taskEventPrefix;
    @Value("${com.dwarfeng.logicengine.cache.prefix.entity.task_variable}")
    private String taskVariablePrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Section, FastJsonSection> sectionRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonSection>) template,
                new LongIdStringKeyFormatter(sectionPrefix),
                new MapStructBeanTransformer<>(Section.class, FastJsonSection.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StateKey, State, FastJsonState> stateRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonState>) template,
                new StateStringKeyFormatter(statePrefix),
                new MapStructBeanTransformer<>(State.class, FastJsonState.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, DriverInfo, FastJsonDriverInfo> driverInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonDriverInfo>) template,
                new LongIdStringKeyFormatter(driverInfoPrefix),
                new MapStructBeanTransformer<>(DriverInfo.class, FastJsonDriverInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, DriverSupport, FastJsonDriverSupport> driverSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonDriverSupport>) template,
                new StringIdStringKeyFormatter(driverSupportPrefix),
                new MapStructBeanTransformer<>(DriverSupport.class, FastJsonDriverSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, GuarderInfo, FastJsonGuarderInfo> guarderInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonGuarderInfo>) template,
                new LongIdStringKeyFormatter(guarderInfoPrefix),
                new MapStructBeanTransformer<>(GuarderInfo.class, FastJsonGuarderInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, GuarderSupport, FastJsonGuarderSupport>
    guarderSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonGuarderSupport>) template,
                new StringIdStringKeyFormatter(guarderSupportPrefix),
                new MapStructBeanTransformer<>(GuarderSupport.class, FastJsonGuarderSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, PerformerInfo, FastJsonPerformerInfo> performerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPerformerInfo>) template,
                new LongIdStringKeyFormatter(performerInfoPrefix),
                new MapStructBeanTransformer<>(PerformerInfo.class, FastJsonPerformerInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, PerformerSupport, FastJsonPerformerSupport>
    performerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPerformerSupport>) template,
                new StringIdStringKeyFormatter(performerSupportPrefix),
                new MapStructBeanTransformer<>(PerformerSupport.class, FastJsonPerformerSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Task, FastJsonTask> taskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTask>) template,
                new LongIdStringKeyFormatter(taskPrefix),
                new MapStructBeanTransformer<>(Task.class, FastJsonTask.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, TaskEvent, FastJsonTaskEvent> taskEventRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTaskEvent>) template,
                new LongIdStringKeyFormatter(taskEventPrefix),
                new MapStructBeanTransformer<>(TaskEvent.class, FastJsonTaskEvent.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<TaskVariableKey, TaskVariable, FastJsonTaskVariable> taskVariableRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTaskVariable>) template,
                new TaskVariableStringKeyFormatter(taskVariablePrefix),
                new MapStructBeanTransformer<>(TaskVariable.class, FastJsonTaskVariable.class, BeanMapper.class)
        );
    }
}
