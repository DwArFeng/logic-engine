# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
│
├─curator
│      connection.properties
│      latch-path.properties
│
├─database
│      connection.properties
│      performance.properties
│
├─datamark
│      settings.properties
│
├─dubbo
│      connection.properties
│
├─logging
│      README.md
│      settings.xml
│      settings-ref-linux.xml
│      settings-ref-windows.xml
│
├─logic-engine
│      background.properties
│      consume.properties
│      dispatch.properties
│      driver.properties
│      exception.properties
│      launcher.properties
│      purge.properties
│      push.properties
│      receive.properties
│      reset.properties
│      task.properties
│
├─redis
│      connection.properties
│      prefix.properties
│      timeout.properties
│
└─telqos
       connection.properties
```

鉴于大部分配置文件的配置项中都有详细的注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项，
省略的部分将会使用 `etc...` 进行标注。

## curator 目录

| 文件名                | 说明               |
|-----------------------|--------------------|
| connection.properties | Curator 连接配置   |
| latch-path.properties | Curator 领导者锁路径 |

### connection.properties

Curator 连接配置。

```properties
# Zookeeper 地址。
com.dwarfeng.logicengine.curator.connect.connect_string=your-host-here:2181
# 会话超时时间。
com.dwarfeng.logicengine.curator.connect.session_timeout=60000
# 连接超时时间。
com.dwarfeng.logicengine.curator.connect.connection_timeout=15000
# 第一次重试时的间隔时间。
com.dwarfeng.logicengine.curator.retry_policy.base_sleep_time=1000
# 最大重试次数。
com.dwarfeng.logicengine.curator.retry_policy.max_retries=10
# 单次重试最大的间隔时间。
com.dwarfeng.logicengine.curator.retry_policy.max_sleep=60000
```

Curator 连接配置文件，包括 Zookeeper 连接地址、超时时间和重试策略。

### latch-path.properties

Curator 领导者锁路径。

```properties
# 任务检查服务的领导者锁路径。
com.dwarfeng.logicengine.curator.latch_path.task_check.leader_latch=/logic_engine/task_check/leader_latch
# 主管服务的领导者锁路径。
com.dwarfeng.logicengine.curator.latch_path.supervise.leader_latch=/logic_engine/supervise/leader_latch
# 清除服务的领导者锁路径。
com.dwarfeng.logicengine.curator.latch_path.purge.leader_latch=/logic_engine/purge/leader_latch
```

如果您在本机上部署了多个项目，每个项目中都使用本服务，那么需要为每个项目配置不同的领导者锁路径，
以避免项目之间不必要的互斥。

## database 目录

| 文件名                 | 说明               |
|------------------------|--------------------|
| connection.properties  | 数据库连接配置文件 |
| performance.properties | 数据库性能配置文件 |

### connection.properties

数据库连接配置文件，除了标准的数据库配置四要素之外，还包括 Hibernate 的方言配置。

```properties
com.dwarfeng.logicengine.jdbc.driver=com.mysql.cj.jdbc.Driver
com.dwarfeng.logicengine.jdbc.url=\
  jdbc:mysql://your-host-here:3306/logic_engine?serverTimezone=Asia/Shanghai&autoReconnect=true
com.dwarfeng.logicengine.jdbc.username=root
com.dwarfeng.logicengine.jdbc.password=your-password-here
com.dwarfeng.logicengine.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

默认的连接地址使用 MySQL，数据库名称为 `logic_engine`，并通过 `serverTimezone=Asia/Shanghai` 指定时区，
通过 `autoReconnect=true` 开启自动重连。

### performance.properties

数据库性能配置文件，使用默认值即可，或按照实际情况进行修改。

```properties
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
com.dwarfeng.logicengine.hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
com.dwarfeng.logicengine.hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
com.dwarfeng.logicengine.data_source.max_active=20
# 连接池最小空闲连接数量
com.dwarfeng.logicengine.data_source.min_idle=0
```

## datamark 目录

| 文件名              | 说明               |
|---------------------|--------------------|
| settings.properties | 数据标记的配置文件 |

### settings.properties

数据标记的配置文件。

数据标记是本项目的一个运维与安全机制，它使用 `dwarfeng-datamark` 实现，其主要的功能是在重要数据插入/更改时，
向数据库特定的数据标记字段写入特定值，
这个特定值被记录在 `dwarfeng-datamark` 中的 `resource` 中 - 可以是 spring 框架支持的任何资源类型，
支持运行时修改，并对前端完全不可见。

运维人员可以用这个机制降低运维的工作量 - 尤其是从测试环境向正式环境迁移数据时，也可以用这个机制进行数据非法篡改的检测与取证。

```properties
#---------------------------------配置说明----------------------------------------
# 数据标记资源的 URL，格式参考 Spring 资源路径。
# com.dwarfeng.logicengine.datamark.xxx.resource_url=classpath:datamark/default.storage
# 数据标记资源的字符集。
# com.dwarfeng.logicengine.datamark.xxx.resource_charset=UTF-8
# 数据标记服务是否允许更新。
# com.dwarfeng.logicengine.datamark.xxx.update_allowed=true
#
#---------------------------------Section----------------------------------------
com.dwarfeng.logicengine.datamark.section.resource_url=classpath:datamark/default.storage
com.dwarfeng.logicengine.datamark.section.resource_charset=UTF-8
com.dwarfeng.logicengine.datamark.section.update_allowed=true
#
#---------------------------------State----------------------------------------
com.dwarfeng.logicengine.datamark.state.resource_url=classpath:datamark/default.storage
com.dwarfeng.logicengine.datamark.state.resource_charset=UTF-8
com.dwarfeng.logicengine.datamark.state.update_allowed=true
#
#---------------------------------DriverInfo----------------------------------------
com.dwarfeng.logicengine.datamark.driver_info.resource_url=classpath:datamark/default.storage
com.dwarfeng.logicengine.datamark.driver_info.resource_charset=UTF-8
com.dwarfeng.logicengine.datamark.driver_info.update_allowed=true
#
#---------------------------------GuarderInfo----------------------------------------
com.dwarfeng.logicengine.datamark.guarder_info.resource_url=classpath:datamark/default.storage
com.dwarfeng.logicengine.datamark.guarder_info.resource_charset=UTF-8
com.dwarfeng.logicengine.datamark.guarder_info.update_allowed=true
#
#---------------------------------PerformerInfo----------------------------------------
com.dwarfeng.logicengine.datamark.performer_info.resource_url=classpath:datamark/default.storage
com.dwarfeng.logicengine.datamark.performer_info.resource_charset=UTF-8
com.dwarfeng.logicengine.datamark.performer_info.update_allowed=true
```

本项目当前为 `Section`、`State`、`DriverInfo`、`GuarderInfo`、`PerformerInfo` 五类数据配置数据标记资源。

## dubbo 目录

| 文件名                | 说明               |
|-----------------------|--------------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
com.dwarfeng.logicengine.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
com.dwarfeng.logicengine.dubbo.registry.zookeeper.timeout=3000
com.dwarfeng.logicengine.dubbo.protocol.dubbo.port=20000
com.dwarfeng.logicengine.dubbo.protocol.dubbo.host=your-host-here
com.dwarfeng.logicengine.dubbo.provider.group=
com.dwarfeng.logicengine.dubbo.consumer.snowflake.group=
```

其中，`com.dwarfeng.logicengine.dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`com.dwarfeng.logicengine.dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 logic-engine 实例，那么需要为每个实例配置不同的
`com.dwarfeng.logicengine.dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 logic-engine，那么需要为每个项目配置不同的
`com.dwarfeng.logicengine.dubbo.provider.group`，以避免微服务错误地调用。

`com.dwarfeng.logicengine.dubbo.consumer.snowflake.group` 用于配置 Snowflake 生成服务引用的分组，
需要与实际提供该服务的分组保持一致。

## logic-engine 目录

| 文件名                | 说明                                       |
|-----------------------|--------------------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它 |
| consume.properties    | 消费服务配置文件                           |
| dispatch.properties   | 调度器配置文件                             |
| driver.properties     | 驱动器配置文件                             |
| exception.properties  | ServiceException 的异常代码的偏移量配置    |
| launcher.properties   | 启动器配置文件                             |
| purge.properties      | 清除服务配置文件                           |
| push.properties       | 推送服务配置文件                           |
| receive.properties    | 接收器配置文件                             |
| reset.properties      | 重置服务配置文件                           |
| task.properties       | 任务配置文件                               |

### background.properties

后台服务配置文件，包括线程池的线程数及其它。

```properties
# 任务执行器的线程池数量范围。
com.dwarfeng.logicengine.executor.pool_size=20-40
# 任务执行器的队列容量。
com.dwarfeng.logicengine.executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
com.dwarfeng.logicengine.executor.keep_alive=120
# 计划执行器的线程池数量范围。
com.dwarfeng.logicengine.scheduler.pool_size=10
```

### consume.properties

消费服务配置文件，核心配置之一。

```properties
# 当消费者中的待消费元素超过缓存上限指定比例后，向日志中输入警告信息。
com.dwarfeng.logicengine.consume.threshold.warn=0.8
# 消费者线程数：线程数越大，处理的能力越强，服务器负荷越重。
com.dwarfeng.logicengine.consume.consumer_thread=1
# 缓存大小：缓存越大抗波动能力越强，数据实时性越低。
# 当缓存被占满时，会导致消费者阻塞，此时如果 rpc 的超时设置不当，容易引起数据重复提交或者丢失的问题。
# 数据占满缓存这一现象是需要尽力避免的，程序将在缓存占用量超过指定值的时候发出警报。
com.dwarfeng.logicengine.consume.buffer_size=1000
```

本配置中的参数直接决定了消费服务的性能，您需要根据您的实际情况进行调整。

为了更加直观的观察调整后的效果，本服务提供了关于消费服务的 telnet 指令，您可以使用指令在 telnet 运维系统中动态修改参数，
并观察修改后的效果。在运维系统中的更改重启后会失效，因此，当您将参数调整到满意的程度后，您需要将参数修改到配置文件中。

### dispatch.properties

调度器配置文件，核心配置之一。

```properties
###################################################
#                     global                      #
###################################################
# 当前的调度器类型。
# 目前该项目支持的调度器类型有:
#   drain: 丢弃所有调度请求并记录日志的调度器，用于测试和调试。
#   injvm: 虚拟机内部调度器，用于单节点服务。
#   kafka: 基于 Kafka 实现的调度器，利用 Kafka 消费组机制实现多个接收节点的负载均衡。
#   dubbo: 基于 Dubbo 实现的调度器，利用服务提供者机制实现多个接收节点的负载均衡。
#
# 对于一个具体的项目，很可能只使用一个调度器。此时如果希望程序加载时只加载一个调度器，可以通过编辑
# opt/opt-dispatcher.xml 文件实现。
com.dwarfeng.logicengine.dispatcher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain 调度器没有任何配置。
#
###################################################
#                      injvm                      #
###################################################
# injvm 调度器没有任何配置。
#
###################################################
#                      kafka                      #
###################################################
# 引导服务器集群。
com.dwarfeng.logicengine.dispatcher.kafka.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 生产者与服务器的确认模式，可选值为: 0、1、all。
#   0: 生产者不等待服务器确认，继续发送下一条消息。
#   1: 生产者等待首领副本确认，继续发送下一条消息。
#   all: 生产者等待服务器及其所有同步副本确认，继续发送下一条消息。
com.dwarfeng.logicengine.dispatcher.kafka.acks=all
# 发送失败重试次数，acks 设置为 0 时不生效。
com.dwarfeng.logicengine.dispatcher.kafka.retries=3
# 生产者在发送批处理前等待更多消息加入批处理的时间，单位为毫秒。
com.dwarfeng.logicengine.dispatcher.kafka.linger=10
# 生产者可用于缓冲待发送记录的内存总量，单位为字节。
com.dwarfeng.logicengine.dispatcher.kafka.buffer_memory=40960
# 同一分区发送批次的目标大小，单位为字节。
com.dwarfeng.logicengine.dispatcher.kafka.batch_size=4096
# Kafka 事务 ID 前缀，同一 Kafka 集群中的不同应用实例应使用不同前缀。
com.dwarfeng.logicengine.dispatcher.kafka.transaction_prefix=logic_engine.dispatcher.
# 负载均衡策略，可选值为: default、round_robin、random。
#   default: 使用 Kafka 生产者默认分区策略。
#   round_robin: 按当前主题分区列表轮询发送。
#   random: 从当前主题分区列表中随机选择分区发送。
com.dwarfeng.logicengine.dispatcher.kafka.load_balance_mode=default
# 刷新主题分区信息的间隔时间，单位为毫秒。
com.dwarfeng.logicengine.dispatcher.kafka.partition_check_interval=30000
# 执行调度时向 Kafka 发送消息的主题，应与 Kafka 接收器监听的主题保持一致。
com.dwarfeng.logicengine.dispatcher.kafka.topic.dispatch=logic_engine.dispatcher.dispatch
#
###################################################
#                      dubbo                      #
###################################################
# dubbo 调度器没有任何独立配置，使用 dubbo/connection.properties 中的注册中心和提供者分组配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-dispatcher.xml`，决定项目中需要使用哪些调度器。您只需要修改使用的调度器的配置。

### driver.properties

驱动器配置文件，核心配置之一。

```properties
###################################################
#                      cron                       #
###################################################
# Cron 驱动没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# FixedDelay 驱动没有任何配置。
#
###################################################
#                    fixed_rate                   #
###################################################
# FixedRate 驱动没有任何配置。
#
###################################################
#                    kafka.dcti                   #
###################################################
# 引导服务器集群。
com.dwarfeng.logicengine.driver.kafka.dcti.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
com.dwarfeng.logicengine.driver.kafka.dcti.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
com.dwarfeng.logicengine.driver.kafka.dcti.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
com.dwarfeng.logicengine.driver.kafka.dcti.concurrency=2
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
com.dwarfeng.logicengine.driver.kafka.dcti.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
# 该设置会覆盖 kafka 的 group.id 设置，因此无需设置 group.id。
com.dwarfeng.logicengine.driver.kafka.dcti.listener_id=logicengine.driver.dcti
# 监听器的目标 topic。
com.dwarfeng.logicengine.driver.kafka.dcti.listener_topic=dcti.data_info
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
com.dwarfeng.logicengine.driver.kafka.dcti.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
com.dwarfeng.logicengine.driver.kafka.dcti.max_poll_interval_ms=300000
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-driver.xml`，决定项目中需要使用哪些驱动器。您只需要修改使用的驱动器的配置。

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# logic-engine 工程自身的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset=1000
# logic-engine 工程中 subgrade 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.subgrade=0
# logic-engine 工程中 spring-telqos 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.spring_telqos=2000
# logic-engine 工程中 spring-terminator 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.spring_terminator=3000
# logic-engine 工程中 dwarfeng-datamark 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.dwarfeng_datamark=4000
# logic-engine 工程中 dcti 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.dcti=5000
# logic-engine 工程中 dwarfeng-dct 的异常代号偏移量。
com.dwarfeng.logicengine.logic_engine.exception_code_offset.dwarfeng_dct=6000
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

### launcher.properties

启动器配置文件，决定了启动时的一些行为。

```properties
# 程序启动完成后，是否重置执行器支持。
com.dwarfeng.logicengine.launcher.reset_performer_support=true
#
# 程序启动完成后，是否重置守卫器支持。
com.dwarfeng.logicengine.launcher.reset_guarder_support=true
#
# 程序启动完成后，是否重置驱动器支持。
com.dwarfeng.logicengine.launcher.reset_driver_support=true
#
# 程序启动完成后，上线任务检查的延时时间。
# 有些数据仓库以及任务检查器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即上线任务检查服务。
# 该参数小于 0，意味着程序不主动上线任务检查服务，需要手动上线。
com.dwarfeng.logicengine.launcher.online_task_check_delay=3000
# 程序启动完成后，启动任务检查的延时时间。
# 有些数据仓库以及任务检查器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动任务检查服务。
# 该参数小于 0，意味着程序不主动启动任务检查服务，需要手动启动。
com.dwarfeng.logicengine.launcher.enable_task_check_delay=3500
#
# 程序启动完成后，启动接收的延时时间。
# 有些数据仓库以及接收处理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动接收服务。
# 该参数小于 0，意味着程序不主动启动接收服务，需要手动启动。
com.dwarfeng.logicengine.launcher.start_receive_delay=4000
#
# 程序启动完成后，上线主管的延时时间。
# 该参数等于 0，意味着启动后立即上线主管服务。
# 该参数小于 0，意味着程序不主动上线主管服务，需要手动上线。
com.dwarfeng.logicengine.launcher.online_supervise_delay=4500
# 程序启动完成后，启动主管的延时时间。
# 该参数等于 0，意味着启动后立即启动主管服务。
# 该参数小于 0，意味着程序不主动启动主管服务，需要手动启动。
com.dwarfeng.logicengine.launcher.enable_supervise_delay=5000
#
# 程序启动完成后，上线清除服务的延时时间。
# 该参数等于 0，意味着启动后立即上线清除服务。
# 该参数小于 0，意味着程序不主动上线清除服务，需要手动上线。
com.dwarfeng.logicengine.launcher.online_purge_delay=5500
# 程序启动完成后，启动清除服务的延时时间。
# 该参数等于 0，意味着启动后立即启动清除服务。
# 该参数小于 0，意味着程序不主动启动清除服务，需要手动启动。
com.dwarfeng.logicengine.launcher.enable_purge_delay=6000
#
# 程序启动完成后，启动重置服务的延时时间。
# 有些数据仓库以及重置器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善地处理这些数据源和重置器。
# 该参数等于 0，意味着启动后立即启动重置服务。
# 该参数小于 0，意味着程序不主动启动重置服务，需要手动启动。
com.dwarfeng.logicengine.launcher.start_reset_delay=30000
```

该配置文件决定了服务被运行后，哪些功能将会自动被执行。

对于负载巨大场景，需要服务集群做读写分离，一部分服务在启动后自动执行数据业务并下线微服务（下线微服务当前还未自动化），
专注于业务的处理；另一部分则不执行数据业务并上线微服务，专注于响应调用方。

自动执行是可选的配置功能，任何启动时没有自动执行的功能模块，均可以通过服务的 telqos 系统随时进行启用。

### purge.properties

清除服务配置文件。

```properties
# 清除任务的保留时长（毫秒）。
# 发生日期距离当前系统日期超过该时长的历史数据将被清除。
# 如果设置为 0 或负数，清除计划将不启动。
com.dwarfeng.logicengine.purge.retention_duration=17280000000
# 清除任务的执行周期（Cron 表达式）。
com.dwarfeng.logicengine.purge.task_cron=0 0 2 * * ?
# 清除任务的最大分页大小。
# 每次查询待清除数据时的最大数量。
com.dwarfeng.logicengine.purge.max_page_size=1000
# 清除任务的最大删除数量。
# 单次清除任务最多删除的数据条数。
com.dwarfeng.logicengine.purge.max_deletion_size=10000
```

清除服务用于定时清除产生时间过久的历史数据，是保证服务数据量不持续增加的机制。

当前清除服务按照先后顺序清除以下实体：

1. 任务，即 `Task`。

配置项 `com.dwarfeng.logicengine.purge.retention_duration` 用于指定清除任务的保留时长，是清除任务中最重要的配置项。
默认值为 `17280000000`，约为 200 天。如果设置为 `0` 或负数，清除计划将不启动。

配置项 `com.dwarfeng.logicengine.purge.task_cron` 用于设置清除任务的执行周期（Cron 表达式），默认每天 2 点执行。

为了提升清除服务的性能，清除服务分批查数，并分批清除。

配置项 `com.dwarfeng.logicengine.purge.max_page_size` 用于指定清除服务分批清除时，每批数据的数量，
可根据数据库性能及服务器的性能综合决定此值。

配置项 `com.dwarfeng.logicengine.purge.max_deletion_size` 用于指定单次任务最多删除的数据条数，此值可保证每次任务执行时，
都会在一定的时间内结束，不会因为某次需要删除的数据量过大而长时间执行，以至于影响正常业务。

如果单次清除任务达到最大删除数量后仍然存在待清除数据，清除处理器会记录清除发散信息，
提示使用者减少清除任务的执行间隔或增加最大删除数量，以避免数据积压。

### push.properties

推送服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的推送器类型。
# 目前该项目支持的推送器类型有:
#   drain: 丢弃所有推送事件的推送器，用于不需要对外推送的场景。
#   log: 将推送事件输出至日志的推送器，用于测试和调试。
#   multi: 将推送事件依次发送给多个代理推送器。
#   kafka.native: 使用独立 Kafka 生产者发送推送事件的推送器。
#
# 对于一个具体的项目，很可能只使用一个推送器。此时如果希望程序加载时只加载一个推送器，可以通过编辑
# opt/opt-pusher.xml 文件实现。
com.dwarfeng.logicengine.pusher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain 推送器没有任何配置。
#
###################################################
#                      multi                      #
###################################################
# 代理推送器类型，多个推送器之间以逗号分隔。
com.dwarfeng.logicengine.pusher.multi.delegate_types=drain
#
###################################################
#                   kafka.native                  #
###################################################
# 引导服务器集群。
com.dwarfeng.logicengine.pusher.kafka.native.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 生产者与服务器的确认模式，可选值为: 0、1、all。
com.dwarfeng.logicengine.pusher.kafka.native.acks=all
# 发送失败重试次数，acks 设置为 0 时不生效。
com.dwarfeng.logicengine.pusher.kafka.native.retries=3
# 生产者在发送批处理前等待更多消息加入批处理的时间，单位为毫秒。
com.dwarfeng.logicengine.pusher.kafka.native.linger=10
# 生产者可用于缓冲待发送记录的内存总量，单位为字节。
com.dwarfeng.logicengine.pusher.kafka.native.buffer_memory=40960
# 同一分区发送批次的目标大小，单位为字节。
com.dwarfeng.logicengine.pusher.kafka.native.batch_size=4096
# Kafka 事务 ID 前缀，同一 Kafka 集群中的不同应用实例应使用不同前缀。
com.dwarfeng.logicengine.pusher.kafka.native.transaction_prefix=logic_engine.pusher.
# 各类推送事件对应的 Kafka 主题。
com.dwarfeng.logicengine.pusher.kafka.native.topic.task_finished=logic_engine.pusher.task_finished
com.dwarfeng.logicengine.pusher.kafka.native.topic.task_failed=logic_engine.pusher.task_failed
com.dwarfeng.logicengine.pusher.kafka.native.topic.task_expired=logic_engine.pusher.task_expired
com.dwarfeng.logicengine.pusher.kafka.native.topic.task_died=logic_engine.pusher.task_died
com.dwarfeng.logicengine.pusher.kafka.native.topic.supervise_reset=logic_engine.pusher.supervise_reset
com.dwarfeng.logicengine.pusher.kafka.native.topic.job_reset=logic_engine.pusher.job_reset
com.dwarfeng.logicengine.pusher.kafka.native.topic.purge_finished=logic_engine.pusher.purge_finished
com.dwarfeng.logicengine.pusher.kafka.native.topic.purge_failed=logic_engine.pusher.purge_failed
#
###################################################
#                       log                       #
###################################################
# 推送日志的等级，可选值为 TRACE、DEBUG、INFO、WARN、ERROR。
com.dwarfeng.logicengine.pusher.log.log_level=INFO
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-pusher.xml`，决定项目中需要使用哪些推送器。您只需要修改使用的推送器的配置。

### receive.properties

接收器配置文件，核心配置之一。

```properties
###################################################
#                     global                      #
###################################################
# 当前的接收器类型。
# 目前该项目支持的接收器类型有:
#   do_nothing: 什么也不做的接收器，用于测试。
#   injvm: 虚拟机内部接收器，用于单节点服务。
#   kafka: 基于 Kafka 实现的接收器，利用 Kafka 的消费者机制实现多个接收节点的负载均衡。
#   dubbo: 基于 Dubbo 实现的接收器，利用 Dubbo 的服务提供者机制实现多个接收节点的负载均衡。
#
# 对于一个具体的项目，很可能只用一个接收器。此时如果希望程序加载时只加载一个接收器，可以通过编辑
# opt/opt-receiver.xml 文件实现。
com.dwarfeng.logicengine.receiver.type=do_nothing
#
###################################################
#                   do_nothing                    #
###################################################
# do_nothing 接收器没有任何配置。
#
###################################################
#                      injvm                      #
###################################################
# injvm 接收器没有任何配置。
#
###################################################
#                      kafka                      #
###################################################
# 引导服务器集群。
com.dwarfeng.logicengine.receiver.kafka.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
com.dwarfeng.logicengine.receiver.kafka.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
com.dwarfeng.logicengine.receiver.kafka.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
com.dwarfeng.logicengine.receiver.kafka.concurrency=1
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
com.dwarfeng.logicengine.receiver.kafka.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
com.dwarfeng.logicengine.receiver.kafka.listener_id=logic_engine.receiver
# 监听器的目标 topic。
com.dwarfeng.logicengine.receiver.kafka.listener_topic=logic_engine.dispatcher.dispatch
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
com.dwarfeng.logicengine.receiver.kafka.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
com.dwarfeng.logicengine.receiver.kafka.max_poll_interval_ms=300000
#
###################################################
#                      dubbo                      #
###################################################
# dubbo 接收器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-receiver.xml`，决定项目中需要使用哪些接收器。您只需要修改使用的接收器的配置。

### reset.properties

重置服务配置文件。

```properties
###################################################
#                      never                      #
###################################################
# Never 重置器没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
com.dwarfeng.logicengine.resetter.fixed_delay.delay=43200000
#
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
com.dwarfeng.logicengine.resetter.fixed_rate.rate=43200000
#
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
com.dwarfeng.logicengine.resetter.cron.cron=0 0 1 * * *
#
###################################################
#                      dubbo                      #
###################################################
# Dubbo 重置器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-resetter.xml`，决定项目中需要使用哪些重置器。您只需要修改使用的重置器的配置。

### task.properties

任务配置文件。

```properties
# 任务心跳死亡的全局超时时间。
com.dwarfeng.logicengine.task.die_timeout=3600000
# 任务心跳周期。
com.dwarfeng.logicengine.task.beat_interval=10000
#
# 任务过期检查 cron 表达式。
com.dwarfeng.logicengine.task.check.expire_check.cron=0 * * * * ?
# 任务死亡检查 cron 表达式。
com.dwarfeng.logicengine.task.check.die_check.cron=0 * * * * ?
```

任务配置文件用于配置任务的生命周期管理相关参数。

配置项 `com.dwarfeng.logicengine.task.die_timeout` 用于指定任务心跳死亡的全局超时时间。任务每次发送心跳时，
都会根据该值刷新自身的预期死亡时间。

配置项 `com.dwarfeng.logicengine.task.beat_interval` 用于指定任务的心跳周期。任务执行过程中会定期发送心跳，
以表明任务仍在执行中。

配置项 `com.dwarfeng.logicengine.task.check.expire_check.cron` 和
`com.dwarfeng.logicengine.task.check.die_check.cron` 用于指定任务过期检查和死亡检查的执行周期（Cron 表达式）。

## logging 目录

| 文件名                   | 说明                                 |
|--------------------------|--------------------------------------|
| README.md                | 说明文件                             |
| settings.xml             | 日志配置的配置文件                   |
| settings-ref-linux.xml   | Linux 系统中日志配置的配置参考文件   |
| settings-ref-windows.xml | Windows 系统中日志配置的配置参考文件 |

### README.md

日志配置文件夹的说明文件。

该目录存放了日志配置文件及其不同操作系统的参考配置文件，以供使用者自定义日志配置。

该目录下 `settings.xml` 是日志配置文件的主文件，使用者可以通过修改该文件来自定义日志配置。

该目录下 `settings-ref-*.xml` 是不同操作系统的参考配置文件。

使用者可以根据自己的需求将对应的参考配置文件中的内容复制到 `settings.xml` 中以应用对应操作系统的默认配置，
或者直接修改 `settings.xml` 以自定义日志配置。

### settings.xml

日志配置及其参考文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!-- etc... -->
    </Appenders>

    <Loggers>
        <!-- etc... -->
    </Loggers>
</Configuration>
```

需要注意的是，日志配置 **必须** 定义在 `settings.xml` 中才能生效，所有的 `settings-ref-xxx.xml` 都是参考文件，
在这些文件中进行任何配置的修改 **均不会生效**。

常用的做法是，针对不同的操作系统，将参考文件中的内容直接复制到 `settings.xml` 中，随后对 `settings.xml` 中的内容进行修改。

- 如果服务运行一天产生的日志超过了配置上限，可上调 `rolling_file.rollover.max` 参数。
- 如果存在等保需求，日志至少需要保留 6 个月，需要调整 `rolling_file.rollover.delete_age` 参数至 `200D`。

### settings-ref-linux.xml

Linux 系统中的日志配置参考文件。相对于默认的 `settings.xml`，该参考文件将滚动日志目录配置为 `/var/log/logic-engine`。
控制台输出编码和滚动文件编码均为 `UTF-8`。

### settings-ref-windows.xml

Windows 系统中的日志配置参考文件。相对于默认的 `settings.xml`，该参考文件将控制台输出文本的编码配置为 `GBK`，
滚动文件编码仍然为 `UTF-8`。

## redis 目录

| 文件名                | 说明     |
|-----------------------|----------|
| connection.properties | 连接配置 |
| prefix.properties     | 前缀配置 |
| timeout.properties    | 超时配置 |

### connection.properties

Redis 连接配置文件。

```properties
# ip 地址。
com.dwarfeng.logicengine.redis.hostName=your-host-here
# 端口号。
com.dwarfeng.logicengine.redis.port=6379
# 如果有密码。
com.dwarfeng.logicengine.redis.password=your-password-here
# 客户端超时时间单位是毫秒 默认是 2000。
com.dwarfeng.logicengine.redis.timeout=10000
# 最大空闲数。
com.dwarfeng.logicengine.redis.maxIdle=300
# 连接池的最大数据库连接数。设为 0 表示无限制，如果是 jedis 2.4 以后用 redis.maxTotal。
# com.dwarfeng.logicengine.redis.maxActive=600
# 控制一个 pool 可分配多少个 jedis 实例，用来替换上面的 redis.maxActive，如果是 jedis 2.4 以后用该属性。
com.dwarfeng.logicengine.redis.maxTotal=1000
# 最大建立连接等待时间。如果超过此时间将接到异常。设为-1 表示无限制。
com.dwarfeng.logicengine.redis.maxWaitMillis=1000
# 连接的最小空闲时间 默认 1800000 毫秒(30 分钟)。
com.dwarfeng.logicengine.redis.minEvictableIdleTimeMillis=300000
# 每次释放连接的最大数目，默认 3。
com.dwarfeng.logicengine.redis.numTestsPerEvictionRun=1024
# 逐出扫描的时间间隔(毫秒) 如果为负数，则不运行逐出线程， 默认 -1。
com.dwarfeng.logicengine.redis.timeBetweenEvictionRunsMillis=30000
# 是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个。
com.dwarfeng.logicengine.redis.testOnBorrow=true
# 在空闲时检查有效性， 默认 false。
com.dwarfeng.logicengine.redis.testWhileIdle=true
```

请根据实际的 Redis 服务修改连接地址、端口和密码。

### prefix.properties

Redis 前缀配置文件。

```properties
#------------------------------------------------------------------------------------
# 缓存键前缀配置。
#------------------------------------------------------------------------------------
# 部件缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.section=com.dwarfeng.logicengine.entity.section.
# 状态缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.state=com.dwarfeng.logicengine.entity.state.
# 驱动器信息缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.driver_info=com.dwarfeng.logicengine.entity.driver_info.
# 驱动器支持缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.driver_support=com.dwarfeng.logicengine.entity.driver_support.
# 守卫器信息缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.guarder_info=com.dwarfeng.logicengine.entity.guarder_info.
# 守卫器支持缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.guarder_support=com.dwarfeng.logicengine.entity.guarder_support.
# 执行器信息缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.performer_info=com.dwarfeng.logicengine.entity.performer_info.
# 执行器支持缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.performer_support=com.dwarfeng.logicengine.entity.performer_support.
# 任务缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.task=com.dwarfeng.logicengine.entity.task.
# 任务事件缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.task_event=com.dwarfeng.logicengine.entity.task_event.
# 任务变量缓存键前缀。
com.dwarfeng.logicengine.cache.prefix.entity.task_variable=com.dwarfeng.logicengine.entity.task_variable.
```

Redis 利用该配置文件，为缓存的主键添加前缀，以示区分。

如果您的项目包含其它使用 Redis 的模块，您可以修改该配置文件，以避免不同项目的同名实体前缀冲突，相互覆盖。

### timeout.properties

Redis 缓存的超时配置文件。

```properties
#------------------------------------------------------------------------------------
# 缓存超时配置。
#------------------------------------------------------------------------------------
# 部件对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.section=3600000
# 状态对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.state=3600000
# 驱动器信息对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.driver_info=3600000
# 驱动器支持对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.driver_support=3600000
# 守卫器信息对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.guarder_info=3600000
# 守卫器支持对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.guarder_support=3600000
# 执行器信息对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.performer_info=3600000
# 执行器支持对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.performer_support=3600000
# 任务对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.task=3600000
# 任务事件对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.task_event=3600000
# 任务变量对象缓存的超时时间。
com.dwarfeng.logicengine.cache.timeout.entity.task_variable=3600000
```

如果您希望缓存更快或更慢地过期，您可以修改该配置文件。

## telqos 目录

| 文件名                | 说明     |
|-----------------------|----------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
# Telnet 端口。
com.dwarfeng.logicengine.telqos.port=23
# 字符集。
com.dwarfeng.logicengine.telqos.charset=UTF-8
# 白名单表达式。
com.dwarfeng.logicengine.telqos.whitelist_regex=
# 黑名单表达式。
com.dwarfeng.logicengine.telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `com.dwarfeng.logicengine.telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `com.dwarfeng.logicengine.telqos.charset` 的值，以避免乱码。
一般情况下，打包配置默认使用 `UTF-8`。
如需按操作系统终端或 Telqos 客户端字符集调整，Windows 环境可考虑 `GBK`，Linux 环境通常使用 `UTF-8`。

如果您希望限制 Telqos 的使用范围，
您可以修改 `com.dwarfeng.logicengine.telqos.whitelist_regex` 和 `com.dwarfeng.logicengine.telqos.blacklist_regex` 的值。
