package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
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
public class StateMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;

    private Section section;
    private List<State> states;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        states = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            State state = new State(
                    new StateKey(null, "test.state." + i), "state", 0, 0L, 1000L, "remark"
            );
            states.add(state);
        }
    }

    @After
    public void tearDown() {
        section = null;
        states.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            for (State state : states) {
                state.getKey().setSectionLongId(section.getKey().getLongId());
                state.setKey(stateMaintainService.insertOrUpdate(state));
                assertTrue(stateMaintainService.exists(state.getKey()));
                State testState = stateMaintainService.get(state.getKey());
                assertEquals(BeanUtils.describe(state), BeanUtils.describe(testState));

                state.setRemark("updated");
                stateMaintainService.update(state);
                testState = stateMaintainService.get(state.getKey());
                assertEquals(BeanUtils.describe(state), BeanUtils.describe(testState));
            }
            for (State state : states) {
                stateMaintainService.delete(state.getKey());
                assertFalse(stateMaintainService.exists(state.getKey()));
            }
        } finally {
            for (State state : states) {
                if (Objects.nonNull(state.getKey())) {
                    stateMaintainService.deleteIfExists(state.getKey());
                }
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
            for (State state : states) {
                state.getKey().setSectionLongId(section.getKey().getLongId());
                state.setKey(stateMaintainService.insertOrUpdate(state));
            }
            assertEquals(
                    states.size(),
                    stateMaintainService.lookupAsList(
                            StateMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );

            sectionMaintainService.deleteIfExists(section.getKey());

            assertEquals(
                    0,
                    stateMaintainService.lookupAsList(
                            StateMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );
            for (State state : states) {
                assertFalse(stateMaintainService.exists(state.getKey()));
            }
        } finally {
            for (State state : states) {
                if (Objects.nonNull(state.getKey())) {
                    stateMaintainService.deleteIfExists(state.getKey());
                }
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
