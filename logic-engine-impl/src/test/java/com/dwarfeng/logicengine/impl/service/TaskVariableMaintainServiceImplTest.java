package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskVariableMaintainService;
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
public class TaskVariableMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskVariableMaintainService taskVariableMaintainService;

    private Section section;
    private State state;
    private Task task;
    private List<TaskVariable> taskVariables;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        state = new State(new StateKey(null, "test"), "state", 0, 0L, 1000L, "remark");
        task = new Task(
                null, null, null, 0, new Date(0L), new Date(1000L), null, null,
                new Date(60000L), new Date(120000L), null, null, new Date(1000L), "message"
        );
        taskVariables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskVariables.add(new TaskVariable(
                    new TaskVariableKey(null, "variableStringId." + i),
                    0, "value", null, null, null, null
            ));
        }
    }

    @After
    public void tearDown() {
        section = null;
        state = null;
        task = null;
        taskVariables.clear();
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
            for (TaskVariable taskVariable : taskVariables) {
                taskVariable.getKey().setTaskLongId(task.getKey().getLongId());
                taskVariable.setKey(taskVariableMaintainService.insertOrUpdate(taskVariable));
                assertTrue(taskVariableMaintainService.exists(taskVariable.getKey()));
                TaskVariable testTaskVariable = taskVariableMaintainService.get(taskVariable.getKey());
                assertEquals(BeanUtils.describe(taskVariable), BeanUtils.describe(testTaskVariable));

                taskVariable.setStringValue("updated");
                taskVariableMaintainService.update(taskVariable);
                testTaskVariable = taskVariableMaintainService.get(taskVariable.getKey());
                assertEquals(BeanUtils.describe(taskVariable), BeanUtils.describe(testTaskVariable));
            }
            for (TaskVariable taskVariable : taskVariables) {
                taskVariableMaintainService.delete(taskVariable.getKey());
                assertFalse(taskVariableMaintainService.exists(taskVariable.getKey()));
            }
        } finally {
            for (TaskVariable taskVariable : taskVariables) {
                if (Objects.nonNull(taskVariable.getKey())) {
                    taskVariableMaintainService.deleteIfExists(taskVariable.getKey());
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
            for (TaskVariable taskVariable : taskVariables) {
                taskVariable.getKey().setTaskLongId(task.getKey().getLongId());
                taskVariable.setKey(taskVariableMaintainService.insertOrUpdate(taskVariable));
            }
            assertEquals(
                    taskVariables.size(),
                    taskVariableMaintainService.lookupAsList(
                            TaskVariableMaintainService.CHILD_FOR_TASK, new Object[]{task.getKey()}
                    ).size()
            );

            com.dwarfeng.subgrade.stack.bean.key.LongIdKey taskKey = task.getKey();
            taskMaintainService.deleteIfExists(taskKey);

            assertEquals(
                    0,
                    taskVariableMaintainService.lookupAsList(
                            TaskVariableMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}
                    ).size()
            );
            for (TaskVariable taskVariable : taskVariables) {
                assertFalse(taskVariableMaintainService.exists(taskVariable.getKey()));
            }
        } finally {
            for (TaskVariable taskVariable : taskVariables) {
                if (Objects.nonNull(taskVariable.getKey())) {
                    taskVariableMaintainService.deleteIfExists(taskVariable.getKey());
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
