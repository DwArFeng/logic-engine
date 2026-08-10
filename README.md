# logic-engine

logic-engine 是基于 Subgrade 构建的逻辑状态机服务，用于通过 Driver 创建任务， 并由 Worker 按状态、Guarder 与 Performer
配置执行完整状态机。

## 模块

- `logic-engine-stack`：服务接口、实体与异常定义。
- `logic-engine-sdk`：SDK 实体、校验器与通用工具。
- `logic-engine-impl`：维护服务、持久化、缓存和运行配置。
- `logic-engine-node`：节点聚合模块。
- `logic-engine-node-all-he`：包含完整能力的 Hibernate 节点。

## 运行环境

- Java 8。
- MySQL、Redis 与 ZooKeeper。
