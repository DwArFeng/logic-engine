package com.dwarfeng.logicengine.impl.service.operation;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.cache.GuarderInfoCache;
import com.dwarfeng.logicengine.stack.cache.PerformerInfoCache;
import com.dwarfeng.logicengine.stack.cache.StateCache;
import com.dwarfeng.logicengine.stack.cache.TaskCache;
import com.dwarfeng.logicengine.stack.dao.GuarderInfoDao;
import com.dwarfeng.logicengine.stack.dao.PerformerInfoDao;
import com.dwarfeng.logicengine.stack.dao.StateDao;
import com.dwarfeng.logicengine.stack.dao.TaskDao;
import com.dwarfeng.logicengine.stack.service.GuarderInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.PerformerInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StateCrudOperation implements BatchCrudOperation<StateKey, State> {

    private final StateDao stateDao;
    private final StateCache stateCache;

    private final GuarderInfoDao guarderInfoDao;
    private final GuarderInfoCache guarderInfoCache;

    private final PerformerInfoDao performerInfoDao;
    private final PerformerInfoCache performerInfoCache;

    private final TaskDao taskDao;
    private final TaskCache taskCache;

    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.state}")
    private long stateTimeout;

    public StateCrudOperation(
            StateDao stateDao,
            StateCache stateCache,
            GuarderInfoDao guarderInfoDao,
            GuarderInfoCache guarderInfoCache,
            PerformerInfoDao performerInfoDao,
            PerformerInfoCache performerInfoCache,
            TaskDao taskDao,
            TaskCache taskCache
    ) {
        this.stateDao = stateDao;
        this.stateCache = stateCache;
        this.guarderInfoDao = guarderInfoDao;
        this.guarderInfoCache = guarderInfoCache;
        this.performerInfoDao = performerInfoDao;
        this.performerInfoCache = performerInfoCache;
        this.taskDao = taskDao;
        this.taskCache = taskCache;
    }

    @Override
    public boolean exists(StateKey key) throws Exception {
        return stateCache.exists(key) || stateDao.exists(key);
    }

    @Override
    public State get(StateKey key) throws Exception {
        if (stateCache.exists(key)) {
            return stateCache.get(key);
        } else {
            if (!stateDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            State state = stateDao.get(key);
            stateCache.push(state, stateTimeout);
            return state;
        }
    }

    @Override
    public StateKey insert(State state) throws Exception {
        stateCache.push(state, stateTimeout);
        return stateDao.insert(state);
    }

    @Override
    public void update(State state) throws Exception {
        stateCache.push(state, stateTimeout);
        stateDao.update(state);
    }

    @Override
    public void delete(StateKey key) throws Exception {
        // 删除与状态相关的守卫器信息。
        Set<LongIdKey> guarderInfoKeys = new LinkedHashSet<>();
        guarderInfoKeys.addAll(guarderInfoDao.lookup(
                GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{key}
        ).stream().map(GuarderInfo::getKey).collect(Collectors.toList()));
        guarderInfoKeys.addAll(guarderInfoDao.lookup(
                GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{key}
        ).stream().map(GuarderInfo::getKey).collect(Collectors.toList()));
        guarderInfoCache.batchDelete(new ArrayList<>(guarderInfoKeys));
        guarderInfoDao.batchDelete(new ArrayList<>(guarderInfoKeys));

        // 删除与状态相关的执行器信息。
        Set<LongIdKey> performerInfoKeys = new LinkedHashSet<>();
        performerInfoKeys.addAll(performerInfoDao.lookup(
                PerformerInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{key}
        ).stream().map(PerformerInfo::getKey).collect(Collectors.toList()));
        performerInfoKeys.addAll(performerInfoDao.lookup(
                PerformerInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{key}
        ).stream().map(PerformerInfo::getKey).collect(Collectors.toList()));
        performerInfoCache.batchDelete(new ArrayList<>(performerInfoKeys));
        performerInfoDao.batchDelete(new ArrayList<>(performerInfoKeys));

        // 置空与状态相关的任务中的当前状态。
        List<Task> tasks = taskDao.lookup(TaskMaintainService.CHILD_FOR_CURRENT_STATE, new Object[]{key});
        tasks.forEach(task -> task.setCurrentStateKey(null));
        taskDao.batchUpdate(tasks);
        taskCache.batchDelete(tasks.stream().map(Task::getKey).collect(Collectors.toList()));

        // 删除状态自身。
        stateDao.delete(key);
        stateCache.delete(key);
    }

    @Override
    public boolean allExists(List<StateKey> keys) throws Exception {
        return stateCache.allExists(keys) || stateDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StateKey> keys) throws Exception {
        return stateCache.nonExists(keys) && stateDao.nonExists(keys);
    }

    @Override
    public List<State> batchGet(List<StateKey> keys) throws Exception {
        if (stateCache.allExists(keys)) {
            return stateCache.batchGet(keys);
        } else {
            if (!stateDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<State> states = stateDao.batchGet(keys);
            stateCache.batchPush(states, stateTimeout);
            return states;
        }
    }

    @Override
    public List<StateKey> batchInsert(List<State> states) throws Exception {
        stateCache.batchPush(states, stateTimeout);
        return stateDao.batchInsert(states);
    }

    @Override
    public void batchUpdate(List<State> states) throws Exception {
        stateCache.batchPush(states, stateTimeout);
        stateDao.batchUpdate(states);
    }

    @Override
    public void batchDelete(List<StateKey> keys) throws Exception {
        for (StateKey key : keys) {
            delete(key);
        }
    }
}
