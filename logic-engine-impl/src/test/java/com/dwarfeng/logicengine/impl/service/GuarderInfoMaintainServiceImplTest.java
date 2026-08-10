package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.GuarderInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class GuarderInfoMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private GuarderInfoMaintainService guarderInfoMaintainService;

    private Section section;
    private State anchorState;
    private State targetState;
    private List<GuarderInfo> guarderInfos;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        anchorState = new State(new StateKey(null, "anchor"), "state", 0, 0L, 1000L, "remark");
        targetState = new State(new StateKey(null, "target"), "state", 0, 0L, 1000L, "remark");
        guarderInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            guarderInfos.add(new GuarderInfo(null, null, null, null, i, true, "groovy", "true", "remark"));
        }
    }

    @After
    public void tearDown() {
        section = null;
        anchorState = null;
        targetState = null;
        guarderInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            anchorState.getKey().setSectionLongId(section.getKey().getLongId());
            anchorState.setKey(stateMaintainService.insertOrUpdate(anchorState));
            targetState.getKey().setSectionLongId(section.getKey().getLongId());
            targetState.setKey(stateMaintainService.insertOrUpdate(targetState));
            for (GuarderInfo guarderInfo : guarderInfos) {
                guarderInfo.setSectionKey(section.getKey());
                guarderInfo.setAnchorStateKey(anchorState.getKey());
                guarderInfo.setTargetStateKey(targetState.getKey());
                guarderInfo.setKey(guarderInfoMaintainService.insertOrUpdate(guarderInfo));
                assertTrue(guarderInfoMaintainService.exists(guarderInfo.getKey()));
                GuarderInfo testGuarderInfo = guarderInfoMaintainService.get(guarderInfo.getKey());
                assertEquals(BeanUtils.describe(guarderInfo), BeanUtils.describe(testGuarderInfo));

                guarderInfo.setParam("updated");
                guarderInfoMaintainService.update(guarderInfo);
                testGuarderInfo = guarderInfoMaintainService.get(guarderInfo.getKey());
                assertEquals(BeanUtils.describe(guarderInfo), BeanUtils.describe(testGuarderInfo));
            }
            for (GuarderInfo guarderInfo : guarderInfos) {
                guarderInfoMaintainService.delete(guarderInfo.getKey());
                assertFalse(guarderInfoMaintainService.exists(guarderInfo.getKey()));
            }
        } finally {
            for (GuarderInfo guarderInfo : guarderInfos) {
                if (Objects.nonNull(guarderInfo.getKey())) {
                    guarderInfoMaintainService.deleteIfExists(guarderInfo.getKey());
                }
            }
            if (Objects.nonNull(anchorState.getKey())) {
                stateMaintainService.deleteIfExists(anchorState.getKey());
            }
            if (Objects.nonNull(targetState.getKey())) {
                stateMaintainService.deleteIfExists(targetState.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testForSectionCascade() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            anchorState.getKey().setSectionLongId(section.getKey().getLongId());
            anchorState.setKey(stateMaintainService.insertOrUpdate(anchorState));
            targetState.getKey().setSectionLongId(section.getKey().getLongId());
            targetState.setKey(stateMaintainService.insertOrUpdate(targetState));
            for (GuarderInfo guarderInfo : guarderInfos) {
                guarderInfo.setSectionKey(section.getKey());
                guarderInfo.setAnchorStateKey(anchorState.getKey());
                guarderInfo.setTargetStateKey(targetState.getKey());
                guarderInfo.setKey(guarderInfoMaintainService.insertOrUpdate(guarderInfo));
            }
            assertEquals(
                    guarderInfos.size(),
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );

            sectionMaintainService.deleteIfExists(section.getKey());

            assertEquals(
                    0,
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );
            for (GuarderInfo guarderInfo : guarderInfos) {
                assertFalse(guarderInfoMaintainService.exists(guarderInfo.getKey()));
            }
        } finally {
            for (GuarderInfo guarderInfo : guarderInfos) {
                if (Objects.nonNull(guarderInfo.getKey())) {
                    guarderInfoMaintainService.deleteIfExists(guarderInfo.getKey());
                }
            }
            if (Objects.nonNull(anchorState.getKey())) {
                stateMaintainService.deleteIfExists(anchorState.getKey());
            }
            if (Objects.nonNull(targetState.getKey())) {
                stateMaintainService.deleteIfExists(targetState.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testForAnchorStateCascade() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            anchorState.getKey().setSectionLongId(section.getKey().getLongId());
            anchorState.setKey(stateMaintainService.insertOrUpdate(anchorState));
            targetState.getKey().setSectionLongId(section.getKey().getLongId());
            targetState.setKey(stateMaintainService.insertOrUpdate(targetState));
            for (GuarderInfo guarderInfo : guarderInfos) {
                guarderInfo.setSectionKey(section.getKey());
                guarderInfo.setAnchorStateKey(anchorState.getKey());
                guarderInfo.setTargetStateKey(targetState.getKey());
                guarderInfo.setKey(guarderInfoMaintainService.insertOrUpdate(guarderInfo));
            }
            assertEquals(
                    guarderInfos.size(),
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorState.getKey()}
                    ).size()
            );

            stateMaintainService.deleteIfExists(anchorState.getKey());

            assertEquals(
                    0,
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_ANCHOR_STATE, new Object[]{anchorState.getKey()}
                    ).size()
            );
            for (GuarderInfo guarderInfo : guarderInfos) {
                assertFalse(guarderInfoMaintainService.exists(guarderInfo.getKey()));
            }
        } finally {
            for (GuarderInfo guarderInfo : guarderInfos) {
                if (Objects.nonNull(guarderInfo.getKey())) {
                    guarderInfoMaintainService.deleteIfExists(guarderInfo.getKey());
                }
            }
            if (Objects.nonNull(anchorState.getKey())) {
                stateMaintainService.deleteIfExists(anchorState.getKey());
            }
            if (Objects.nonNull(targetState.getKey())) {
                stateMaintainService.deleteIfExists(targetState.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testForTargetStateCascade() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            anchorState.getKey().setSectionLongId(section.getKey().getLongId());
            anchorState.setKey(stateMaintainService.insertOrUpdate(anchorState));
            targetState.getKey().setSectionLongId(section.getKey().getLongId());
            targetState.setKey(stateMaintainService.insertOrUpdate(targetState));
            for (GuarderInfo guarderInfo : guarderInfos) {
                guarderInfo.setSectionKey(section.getKey());
                guarderInfo.setAnchorStateKey(anchorState.getKey());
                guarderInfo.setTargetStateKey(targetState.getKey());
                guarderInfo.setKey(guarderInfoMaintainService.insertOrUpdate(guarderInfo));
            }
            assertEquals(
                    guarderInfos.size(),
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetState.getKey()}
                    ).size()
            );

            stateMaintainService.deleteIfExists(targetState.getKey());

            assertEquals(
                    0,
                    guarderInfoMaintainService.lookupAsList(
                            GuarderInfoMaintainService.CHILD_FOR_TARGET_STATE, new Object[]{targetState.getKey()}
                    ).size()
            );
            for (GuarderInfo guarderInfo : guarderInfos) {
                assertFalse(guarderInfoMaintainService.exists(guarderInfo.getKey()));
            }
        } finally {
            for (GuarderInfo guarderInfo : guarderInfos) {
                if (Objects.nonNull(guarderInfo.getKey())) {
                    guarderInfoMaintainService.deleteIfExists(guarderInfo.getKey());
                }
            }
            if (Objects.nonNull(anchorState.getKey())) {
                stateMaintainService.deleteIfExists(anchorState.getKey());
            }
            if (Objects.nonNull(targetState.getKey())) {
                stateMaintainService.deleteIfExists(targetState.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
