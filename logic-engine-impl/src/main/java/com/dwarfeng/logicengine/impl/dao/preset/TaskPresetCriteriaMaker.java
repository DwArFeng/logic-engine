package com.dwarfeng.logicengine.impl.dao.preset;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskPresetCriteriaMaker implements PresetCriteriaMaker {

    private static final Set<Integer> TO_PURGED_STATUS_SET;

    static {
        Set<Integer> statusSet = new HashSet<>();
        statusSet.add(Constants.TASK_STATUS_FINISHED);
        statusSet.add(Constants.TASK_STATUS_FAILED);
        statusSet.add(Constants.TASK_STATUS_EXPIRED);
        statusSet.add(Constants.TASK_STATUS_DIED);
        TO_PURGED_STATUS_SET = Collections.unmodifiableSet(statusSet);
    }

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case TaskMaintainService.CHILD_FOR_SECTION:
                childForSection(criteria, objs);
                break;
            case TaskMaintainService.CHILD_FOR_CURRENT_STATE:
                childForCurrentState(criteria, objs);
                break;
            case TaskMaintainService.SHOULD_EXPIRE:
                shouldExpire(criteria, objs);
                break;
            case TaskMaintainService.SHOULD_DIE:
                shouldDie(criteria, objs);
                break;
            case TaskMaintainService.TO_PURGED:
                toPurged(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForSection(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("sectionLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("sectionLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForCurrentState(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("currentStateSectionLongId"));
                criteria.add(Restrictions.isNull("currentStateStateId"));
            } else {
                StateKey stateKey = (StateKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("currentStateSectionLongId", stateKey.getSectionLongId()));
                criteria.add(Restrictions.eqOrIsNull("currentStateStateId", stateKey.getStateId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void shouldExpire(DetachedCriteria criteria, Object[] objs) {
        try {
            Date currentDate = new Date();
            criteria.add(Restrictions.in("status", Collections.singletonList(Constants.TASK_STATUS_CREATED)));
            criteria.add(Restrictions.le("shouldExpireDate", currentDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void shouldDie(DetachedCriteria criteria, Object[] objs) {
        try {
            Date currentDate = new Date();
            criteria.add(Restrictions.in("status", Collections.singletonList(Constants.TASK_STATUS_PROCESSING)));
            criteria.add(Restrictions.le("shouldDieDate", currentDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void toPurged(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.add(Restrictions.in("status", TO_PURGED_STATUS_SET));
            Date date = (Date) objs[0];
            criteria.add(Restrictions.lt("endedDate", date));
            criteria.addOrder(Order.asc("endedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
