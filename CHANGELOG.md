# ChangeLog

## Release_1.0.0_20260809_build_A

### 功能构建

- 实现预设守卫器。
  - com.dwarfeng.logicengine.impl.handler.guarder.always.AlwaysGuarderRegistry。
  - com.dwarfeng.logicengine.impl.handler.guarder.groovy.GroovyGuarderRegistry。

- 实现预设执行器。
  - com.dwarfeng.logicengine.impl.handler.performer.groovy.GroovyPerformerRegistry。

- 实现核心机制。
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
