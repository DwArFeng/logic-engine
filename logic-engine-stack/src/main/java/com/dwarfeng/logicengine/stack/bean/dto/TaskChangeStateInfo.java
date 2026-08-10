package com.dwarfeng.logicengine.stack.bean.dto;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务状态变更信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskChangeStateInfo implements Dto {

    private static final long serialVersionUID = -7433933520083611920L;

    /**
     * 任务主键。
     */
    private LongIdKey taskKey;

    /**
     * 目标状态主键。
     */
    private StateKey stateKey;

    public TaskChangeStateInfo() {
    }

    public TaskChangeStateInfo(LongIdKey taskKey, StateKey stateKey) {
        this.taskKey = taskKey;
        this.stateKey = stateKey;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public StateKey getStateKey() {
        return stateKey;
    }

    public void setStateKey(StateKey stateKey) {
        this.stateKey = stateKey;
    }

    @Override
    public String toString() {
        return "TaskChangeStateInfo{" +
                "taskKey=" + taskKey +
                ", stateKey=" + stateKey +
                '}';
    }
}
