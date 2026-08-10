package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TaskMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;

    private Section section;
    private State state;
    private List<Task> tasks;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        state = new State(new StateKey(null, "test"), "state", 0, 0L, 1000L, "remark");
        tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task(
                    null, null, null, 0, new Date(0L), new Date(1000L), null, null,
                    new Date(60000L), new Date(120000L), null, null, new Date(1000L), "message"
            ));
        }
    }

    @After
    public void tearDown() {
        section = null;
        state = null;
        tasks.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            state.getKey().setSectionLongId(section.getKey().getLongId());
            state.setKey(stateMaintainService.insertOrUpdate(state));
            for (Task task : tasks) {
                task.setSectionKey(section.getKey());
                task.setCurrentStateKey(state.getKey());
                task.setKey(taskMaintainService.insertOrUpdate(task));
                assertTrue(taskMaintainService.exists(task.getKey()));
                Task testTask = taskMaintainService.get(task.getKey());
                assertEquals(BeanUtils.describe(task), BeanUtils.describe(testTask));

                task.setAnchorMessage("updated");
                taskMaintainService.update(task);
                testTask = taskMaintainService.get(task.getKey());
                assertEquals(BeanUtils.describe(task), BeanUtils.describe(testTask));
            }
            for (Task task : tasks) {
                taskMaintainService.delete(task.getKey());
                assertFalse(taskMaintainService.exists(task.getKey()));
            }
        } finally {
            for (Task task : tasks) {
                if (Objects.nonNull(task.getKey())) {
                    taskMaintainService.deleteIfExists(task.getKey());
                }
            }
            if (Objects.nonNull(state.getKey())) {
                stateMaintainService.deleteIfExists(state.getKey());
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
            state.getKey().setSectionLongId(section.getKey().getLongId());
            state.setKey(stateMaintainService.insertOrUpdate(state));
            for (Task task : tasks) {
                task.setSectionKey(section.getKey());
                task.setCurrentStateKey(state.getKey());
                task.setKey(taskMaintainService.insertOrUpdate(task));
            }
            assertEquals(
                    tasks.size(),
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );

            sectionMaintainService.deleteIfExists(section.getKey());

            assertEquals(
                    0,
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );
            for (Task task : tasks) {
                assertFalse(taskMaintainService.exists(task.getKey()));
            }
        } finally {
            for (Task task : tasks) {
                if (Objects.nonNull(task.getKey())) {
                    taskMaintainService.deleteIfExists(task.getKey());
                }
            }
            if (Objects.nonNull(state.getKey())) {
                stateMaintainService.deleteIfExists(state.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testForCurrentStateCascade() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            state.getKey().setSectionLongId(section.getKey().getLongId());
            state.setKey(stateMaintainService.insertOrUpdate(state));
            for (Task task : tasks) {
                task.setSectionKey(section.getKey());
                task.setCurrentStateKey(state.getKey());
                task.setKey(taskMaintainService.insertOrUpdate(task));
            }
            assertEquals(
                    tasks.size(),
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_CURRENT_STATE, new Object[]{state.getKey()}
                    ).size()
            );

            stateMaintainService.deleteIfExists(state.getKey());

            assertEquals(
                    0,
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_CURRENT_STATE, new Object[]{state.getKey()}
                    ).size()
            );
            for (Task task : tasks) {
                assertTrue(taskMaintainService.exists(task.getKey()));
                Task testTask = taskMaintainService.get(task.getKey());
                assertNull(testTask.getCurrentStateKey());
            }
        } finally {
            for (Task task : tasks) {
                if (Objects.nonNull(task.getKey())) {
                    taskMaintainService.deleteIfExists(task.getKey());
                }
            }
            if (Objects.nonNull(state.getKey())) {
                stateMaintainService.deleteIfExists(state.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
