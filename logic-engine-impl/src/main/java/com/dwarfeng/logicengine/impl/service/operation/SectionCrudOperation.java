package com.dwarfeng.logicengine.impl.service.operation;

import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.cache.DriverInfoCache;
import com.dwarfeng.logicengine.stack.cache.GuarderInfoCache;
import com.dwarfeng.logicengine.stack.cache.PerformerInfoCache;
import com.dwarfeng.logicengine.stack.cache.SectionCache;
import com.dwarfeng.logicengine.stack.dao.*;
import com.dwarfeng.logicengine.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SectionCrudOperation implements BatchCrudOperation<LongIdKey, Section> {

    private final SectionDao sectionDao;
    private final SectionCache sectionCache;

    private final StateCrudOperation stateCrudOperation;
    private final StateDao stateDao;

    private final DriverInfoDao driverInfoDao;
    private final DriverInfoCache driverInfoCache;

    private final GuarderInfoDao guarderInfoDao;
    private final GuarderInfoCache guarderInfoCache;

    private final PerformerInfoDao performerInfoDao;
    private final PerformerInfoCache performerInfoCache;

    private final TaskCrudOperation taskCrudOperation;
    private final TaskDao taskDao;

    @Value("${com.dwarfeng.logicengine.cache.timeout.entity.section}")
    private long sectionTimeout;

    public SectionCrudOperation(
            SectionDao sectionDao,
            SectionCache sectionCache,
            StateCrudOperation stateCrudOperation,
            StateDao stateDao,
            DriverInfoDao driverInfoDao,
            DriverInfoCache driverInfoCache,
            GuarderInfoDao guarderInfoDao,
            GuarderInfoCache guarderInfoCache,
            PerformerInfoDao performerInfoDao,
            PerformerInfoCache performerInfoCache,
            TaskCrudOperation taskCrudOperation,
            TaskDao taskDao
    ) {
        this.sectionDao = sectionDao;
        this.sectionCache = sectionCache;
        this.stateCrudOperation = stateCrudOperation;
        this.stateDao = stateDao;
        this.driverInfoDao = driverInfoDao;
        this.driverInfoCache = driverInfoCache;
        this.guarderInfoDao = guarderInfoDao;
        this.guarderInfoCache = guarderInfoCache;
        this.performerInfoDao = performerInfoDao;
        this.performerInfoCache = performerInfoCache;
        this.taskCrudOperation = taskCrudOperation;
        this.taskDao = taskDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return sectionCache.exists(key) || sectionDao.exists(key);
    }

    @Override
    public Section get(LongIdKey key) throws Exception {
        if (sectionCache.exists(key)) {
            return sectionCache.get(key);
        } else {
            if (!sectionDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Section section = sectionDao.get(key);
            sectionCache.push(section, sectionTimeout);
            return section;
        }
    }

    @Override
    public LongIdKey insert(Section section) throws Exception {
        sectionCache.push(section, sectionTimeout);
        return sectionDao.insert(section);
    }

    @Override
    public void update(Section section) throws Exception {
        sectionCache.push(section, sectionTimeout);
        sectionDao.update(section);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与部件相关的驱动器信息。
        List<LongIdKey> driverInfoKeys = driverInfoDao.lookup(
                DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key}
        ).stream().map(DriverInfo::getKey).collect(Collectors.toList());
        driverInfoCache.batchDelete(driverInfoKeys);
        driverInfoDao.batchDelete(driverInfoKeys);

        // 删除与部件相关的守卫器信息。
        List<LongIdKey> guarderInfoKeys = guarderInfoDao.lookup(
                GuarderInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key}
        ).stream().map(GuarderInfo::getKey).collect(Collectors.toList());
        guarderInfoCache.batchDelete(guarderInfoKeys);
        guarderInfoDao.batchDelete(guarderInfoKeys);

        // 删除与部件相关的执行器信息。
        List<LongIdKey> performerInfoKeys = performerInfoDao.lookup(
                PerformerInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key}
        ).stream().map(PerformerInfo::getKey).collect(Collectors.toList());
        performerInfoCache.batchDelete(performerInfoKeys);
        performerInfoDao.batchDelete(performerInfoKeys);

        // 删除与部件相关的任务。
        List<LongIdKey> taskKeys = taskDao.lookup(
                TaskMaintainService.CHILD_FOR_SECTION, new Object[]{key}
        ).stream().map(Task::getKey).collect(Collectors.toList());
        taskCrudOperation.batchDelete(taskKeys);

        // 删除与部件相关的状态。
        List<com.dwarfeng.logicengine.stack.bean.key.StateKey> stateKeys = stateDao.lookup(
                StateMaintainService.CHILD_FOR_SECTION, new Object[]{key}
        ).stream().map(State::getKey).collect(Collectors.toList());
        stateCrudOperation.batchDelete(stateKeys);

        // 删除部件自身。
        sectionDao.delete(key);
        sectionCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return sectionCache.allExists(keys) || sectionDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return sectionCache.nonExists(keys) && sectionDao.nonExists(keys);
    }

    @Override
    public List<Section> batchGet(List<LongIdKey> keys) throws Exception {
        if (sectionCache.allExists(keys)) {
            return sectionCache.batchGet(keys);
        } else {
            if (!sectionDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Section> sections = sectionDao.batchGet(keys);
            sectionCache.batchPush(sections, sectionTimeout);
            return sections;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Section> sections) throws Exception {
        sectionCache.batchPush(sections, sectionTimeout);
        return sectionDao.batchInsert(sections);
    }

    @Override
    public void batchUpdate(List<Section> sections) throws Exception {
        sectionCache.batchPush(sections, sectionTimeout);
        sectionDao.batchUpdate(sections);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
