# ChangeLog

## Release_1.0.0_20260809_build_A

### 功能构建

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
