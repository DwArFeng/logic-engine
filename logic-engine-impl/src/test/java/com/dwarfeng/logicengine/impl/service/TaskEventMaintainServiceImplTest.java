package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskEventMaintainService;
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
public class TaskEventMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskEventMaintainService taskEventMaintainService;

    private Section section;
    private State state;
    private Task task;
    private List<TaskEvent> taskEvents;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        state = new State(new StateKey(null, "test"), "state", 0, 0L, 1000L, "remark");
        task = new Task(
                null, null, null, 0, new Date(0L), new Date(1000L), null, null,
                new Date(60000L), new Date(120000L), null, null, new Date(1000L), "message"
        );
        taskEvents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskEvents.add(new TaskEvent(null, null, new Date(1000L), "message"));
        }
    }

    @After
    public void tearDown() {
        section = null;
        state = null;
        task = null;
        taskEvents.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            state.getKey().setSectionLongId(section.getKey().getLongId());
            state.setKey(stateMaintainService.insertOrUpdate(state));
            task.setSectionKey(section.getKey());
            task.setCurrentStateKey(state.getKey());
            task.setKey(taskMaintainService.insertOrUpdate(task));
            for (TaskEvent taskEvent : taskEvents) {
                taskEvent.setTaskKey(task.getKey());
                taskEvent.setKey(taskEventMaintainService.insertOrUpdate(taskEvent));
                assertTrue(taskEventMaintainService.exists(taskEvent.getKey()));
                TaskEvent testTaskEvent = taskEventMaintainService.get(taskEvent.getKey());
                assertEquals(BeanUtils.describe(taskEvent), BeanUtils.describe(testTaskEvent));

                taskEvent.setMessage("updated");
                taskEventMaintainService.update(taskEvent);
                testTaskEvent = taskEventMaintainService.get(taskEvent.getKey());
                assertEquals(BeanUtils.describe(taskEvent), BeanUtils.describe(testTaskEvent));
            }
            for (TaskEvent taskEvent : taskEvents) {
                taskEventMaintainService.delete(taskEvent.getKey());
                assertFalse(taskEventMaintainService.exists(taskEvent.getKey()));
            }
        } finally {
            for (TaskEvent taskEvent : taskEvents) {
                if (Objects.nonNull(taskEvent.getKey())) {
                    taskEventMaintainService.deleteIfExists(taskEvent.getKey());
                }
            }
            if (Objects.nonNull(task.getKey())) {
                taskMaintainService.deleteIfExists(task.getKey());
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
    public void testForTaskCascade() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            state.getKey().setSectionLongId(section.getKey().getLongId());
            state.setKey(stateMaintainService.insertOrUpdate(state));
            task.setSectionKey(section.getKey());
            task.setCurrentStateKey(state.getKey());
            task.setKey(taskMaintainService.insertOrUpdate(task));
            for (TaskEvent taskEvent : taskEvents) {
                taskEvent.setTaskKey(task.getKey());
                taskEvent.setKey(taskEventMaintainService.insertOrUpdate(taskEvent));
            }
            assertEquals(
                    taskEvents.size(),
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{task.getKey()}
                    ).size()
            );

            com.dwarfeng.subgrade.stack.bean.key.LongIdKey taskKey = task.getKey();
            taskMaintainService.deleteIfExists(taskKey);

            assertEquals(
                    0,
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}
                    ).size()
            );
            for (TaskEvent taskEvent : taskEvents) {
                assertFalse(taskEventMaintainService.exists(taskEvent.getKey()));
            }
        } finally {
            for (TaskEvent taskEvent : taskEvents) {
                if (Objects.nonNull(taskEvent.getKey())) {
                    taskEventMaintainService.deleteIfExists(taskEvent.getKey());
                }
            }
            if (Objects.nonNull(task.getKey())) {
                taskMaintainService.deleteIfExists(task.getKey());
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
