package com.dwarfeng.logicengine.impl.dao.preset;

import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.GuarderInfoMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class GuarderInfoPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case GuarderInfoMaintainService.CHILD_FOR_SECTION:
                childForSection(criteria, objs);
                break;
            case GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE:
                childForState(criteria, objs, "anchorStateSectionLongId", "anchorStateStateId");
                break;
            case GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE:
                childForState(criteria, objs, "targetStateSectionLongId", "targetStateStateId");
                break;
            case GuarderInfoMaintainService.SECTION_KEY_ASC_INDEX_ASC:
                sectionKeyAscIndexAsc(criteria, objs);
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

    private void sectionKeyAscIndexAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.addOrder(Order.asc("sectionLongId"));
            criteria.addOrder(Order.asc("index"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForState(
            DetachedCriteria criteria, Object[] objs, String sectionLongIdProperty, String stateIdProperty
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull(sectionLongIdProperty));
                criteria.add(Restrictions.isNull(stateIdProperty));
            } else {
                StateKey stateKey = (StateKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull(sectionLongIdProperty, stateKey.getSectionLongId()));
                criteria.add(Restrictions.eqOrIsNull(stateIdProperty, stateKey.getStateId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
