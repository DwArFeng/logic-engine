# ChangeLog

## Release_1.1.3_20261005_build_A

### 功能构建

- 实现预设守卫器。
  - com.dwarfeng.logicengine.impl.handler.guarder.compare.CompareGuarderRegistry。

- 实现预设执行器。
  - com.dwarfeng.logicengine.impl.handler.performer.cmd.CmdPerformerRegistry。

### Bug 修复

- 修复部分 `opt-*.xml` 文件中错误的配置。
  - opt-guarder.xml。

### 功能移除

- (无)

---

## Release_1.1.2_20261004_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/BatchScripts.md。
  - docs/wiki/zh-CN/ShellScripts.md。

- 增加手动调度能力。
  - com.dwarfeng.logicengine.stack.service.ManualDispatchService。

- 增加预设查询。
  - com.dwarfeng.logicengine.stack.service.TaskMaintainService.CREATED_DATE_DESC。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.1_20260911_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/SystemRequirements.md。

- 优化项目中部分类的代码结构。
  - com.dwarfeng.logicengine.impl.handler.pusher.LogPusher。

- 依赖升级。
  - 升级 `jackson` 依赖版本为 `2.21.5` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.9.0.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `2.0.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `2.0.3.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.1.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.0_20260818_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/VersionBlacklist.md。

- 优化作业机制实现。
  - com.dwarfeng.logicengine.impl.handler.JobHandlerImpl。

- `logic-engine-impl` 子模块配置文件移动至测试目录。
  - com.dwarfeng.logicengine.impl.configuration.CacheConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.DaoConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.ExceptionCodeOffsetConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.FastJsonConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.GenerateConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.ServiceConfiguration。
  - com.dwarfeng.logicengine.impl.configuration.ServiceExceptionMapperConfiguration。

- `logic-engine-stack` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.logicengine.stack.service.DriverSupportMaintainService。
  - com.dwarfeng.logicengine.stack.service.GuarderSupportMaintainService。
  - com.dwarfeng.logicengine.stack.service.PerformerSupportMaintainService。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.0.0_20260815_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/Contents.md。
  - docs/wiki/zh-CN/Introduction.md。
  - docs/wiki/zh-CN/README.md。
  - docs/wiki/en-US/Contents.md。
  - docs/wiki/en-US/Introduction.md。
  - docs/wiki/en-US/README.md。

- `README.md` 更新。

- 完成 `logic-engine-distribute` 模块，打包测试通过。

- 实现运维指令。
  - com.dwarfeng.logicengine.impl.service.telqos.ConsumeCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.DispatchCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.DispatcherCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.DriveCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.DriveLocalCacheCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.JobCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.JobLocalCacheCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.PurgeCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.ReceiveCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.ReceiverCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.ResetCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.SuperviseCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.SupportCommand。
  - com.dwarfeng.logicengine.impl.service.telqos.TaskCheckCommand。

- 实现预设推送器。
  - com.dwarfeng.logicengine.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.logicengine.impl.handler.pusher.LogPusher。
  - com.dwarfeng.logicengine.impl.handler.pusher.MultiPusher。
  - com.dwarfeng.logicengine.impl.handler.pusher.NativeKafkaPusher。

- 实现预设重置器。
  - com.dwarfeng.logicengine.impl.handler.resetter.CronResetter。
  - com.dwarfeng.logicengine.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.logicengine.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.logicengine.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.logicengine.impl.handler.resetter.NeverResetter。

- 实现预设驱动器。
  - com.dwarfeng.logicengine.impl.handler.driver.CronDriverProvider。
  - com.dwarfeng.logicengine.impl.handler.driver.DctiKafkaDriverProvider。
  - com.dwarfeng.logicengine.impl.handler.driver.FixedDelayDriverProvider。
  - com.dwarfeng.logicengine.impl.handler.driver.FixedRateDriverProvider。

- 实现预设调度器。
  - com.dwarfeng.logicengine.impl.handler.dispatcher.DrainDispatcher。
  - com.dwarfeng.logicengine.impl.handler.dispatcher.DubboDispatcher。
  - com.dwarfeng.logicengine.impl.handler.dispatcher.InjvmDispatcher。
  - com.dwarfeng.logicengine.impl.handler.dispatcher.KafkaDispatcher。

- 实现预设接收器。
  - com.dwarfeng.logicengine.impl.handler.receiver.DoNothingReceiver。
  - com.dwarfeng.logicengine.impl.handler.receiver.DubboReceiver。
  - com.dwarfeng.logicengine.impl.handler.receiver.InjvmReceiver。
  - com.dwarfeng.logicengine.impl.handler.receiver.KafkaReceiver。

- 实现预设守卫器。
  - com.dwarfeng.logicengine.impl.handler.guarder.always.AlwaysGuarderRegistry。
  - com.dwarfeng.logicengine.impl.handler.guarder.groovy.GroovyGuarderRegistry。

- 实现预设执行器。
  - com.dwarfeng.logicengine.impl.handler.performer.groovy.GroovyPerformerRegistry。

- 实现核心机制。
  - 推送机制。
  - 重置机制。
  - 清除机制。
  - 主管机制。
  - 驱动机制。
  - 调度机制。
  - 接收机制。
  - 任务检查机制。
  - 作业机制。
  - 守卫机制。
  - 执行机制。

- 完成 `logic-engine-node-all-he` 模块，启动测试通过。

- 建立实体以及维护服务，并通过单元测试。
  - com.dwarfeng.logicengine.stack.bean.entity.DriverInfo。
  - com.dwarfeng.logicengine.stack.bean.entity.DriverSupport。
  - com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo。
  - com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport。
  - com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo。
  - com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport。
  - com.dwarfeng.logicengine.stack.bean.entity.Section。
  - com.dwarfeng.logicengine.stack.bean.entity.State。
  - com.dwarfeng.logicengine.stack.bean.entity.Task。
  - com.dwarfeng.logicengine.stack.bean.entity.TaskEvent。
  - com.dwarfeng.logicengine.stack.bean.entity.TaskVariable。

- 项目结构建立，清理测试通过。

### Bug 修复

- (无)

### 功能移除

- (无)
