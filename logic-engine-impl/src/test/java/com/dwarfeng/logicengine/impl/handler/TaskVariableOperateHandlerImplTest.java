package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableInspectResult;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableRemoveInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.exception.InvalidTaskVariableValueTypeException;
import com.dwarfeng.logicengine.stack.exception.TaskNotExistsException;
import com.dwarfeng.logicengine.stack.exception.TaskVariableNotExistsException;
import com.dwarfeng.logicengine.stack.exception.TaskVariableValueTypeMismatchException;
import com.dwarfeng.logicengine.stack.handler.TaskVariableOperateHandler;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.StateMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskVariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
public class TaskVariableOperateHandlerImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskVariableMaintainService taskVariableMaintainService;
    @Autowired
    private TaskVariableOperateHandler taskVariableOperateHandler;

    private Section section;
    private State state;
    private Task task;
    private List<TaskVariableKey> taskVariableKeys;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        state = new State(new StateKey(null, "test"), "state", 0, 0L, 1000L, "remark");
        task = new Task(
                null, null, null, 0, new Date(0L), new Date(1000L), null, null,
                new Date(60000L), new Date(120000L), null, null, new Date(1000L), "message"
        );
        taskVariableKeys = new ArrayList<>();
    }

    @After
    public void tearDown() {
        section = null;
        state = null;
        task = null;
        taskVariableKeys.clear();
    }

    @Test
    public void testUpsertAndInspect() throws Exception {
        try {
            prepareTask();

            TaskVariableKey stringKey = registerTaskVariableKey("string");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "string", Constants.TASK_VARIABLE_VALUE_TYPE_STRING, "value"
            ));
            assertVariable(stringKey, Constants.TASK_VARIABLE_VALUE_TYPE_STRING, "value", "value");

            TaskVariableKey longKey = registerTaskVariableKey("long");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "long", Constants.TASK_VARIABLE_VALUE_TYPE_LONG, 42L
            ));
            assertVariable(longKey, Constants.TASK_VARIABLE_VALUE_TYPE_LONG, 42L, 42L);

            TaskVariableKey doubleKey = registerTaskVariableKey("double");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "double", Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE, 12.5D
            ));
            assertVariable(doubleKey, Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE, 12.5D, 12.5D);

            TaskVariableKey booleanKey = registerTaskVariableKey("boolean");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "boolean", Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN, true
            ));
            assertVariable(booleanKey, Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN, true, true);

            Date dateValue = new Date(1000L);
            TaskVariableKey dateKey = registerTaskVariableKey("date");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "date", Constants.TASK_VARIABLE_VALUE_TYPE_DATE, dateValue
            ));
            assertVariable(dateKey, Constants.TASK_VARIABLE_VALUE_TYPE_DATE, dateValue, dateValue);
        } finally {
            cleanup();
        }
    }

    @Test
    public void testNullValueIsDifferentFromAbsentVariable() throws Exception {
        try {
            prepareTask();

            TaskVariableKey taskVariableKey = registerTaskVariableKey("null");
            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "null", Constants.TASK_VARIABLE_VALUE_TYPE_STRING, null
            ));

            assertTrue(taskVariableMaintainService.exists(taskVariableKey));
            TaskVariable taskVariable = taskVariableMaintainService.get(taskVariableKey);
            assertEquals(Constants.TASK_VARIABLE_VALUE_TYPE_STRING, taskVariable.getValueType());
            assertNull(taskVariable.getStringValue());

            TaskVariableInspectResult existingResult = taskVariableOperateHandler.inspect(
                    new TaskVariableInspectInfo(task.getKey(), "null")
            );
            assertNotNull(existingResult);
            assertEquals(Constants.TASK_VARIABLE_VALUE_TYPE_STRING, existingResult.getValueType());
            assertNull(existingResult.getValue());
            assertNull(taskVariableOperateHandler.inspect(new TaskVariableInspectInfo(task.getKey(), "absent")));
        } finally {
            cleanup();
        }
    }

    @Test
    public void testTaskMustExist() throws Exception {
        LongIdKey taskKey = new LongIdKey(-1L);
        assertFalse(taskMaintainService.exists(taskKey));

        assertThrows(
                TaskNotExistsException.class,
                () -> taskVariableOperateHandler.inspect(new TaskVariableInspectInfo(taskKey, "variable"))
        );
        assertThrows(
                TaskNotExistsException.class,
                () -> taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                        taskKey, "variable", Constants.TASK_VARIABLE_VALUE_TYPE_STRING, "value"
                ))
        );
        assertThrows(
                TaskNotExistsException.class,
                () -> taskVariableOperateHandler.remove(new TaskVariableRemoveInfo(taskKey, "variable"))
        );
    }

    @Test
    public void testTaskVariableValueTypeMustBeValid() throws Exception {
        try {
            prepareTask();
            TaskVariableKey taskVariableKey = registerTaskVariableKey("variable");

            assertThrows(
                    InvalidTaskVariableValueTypeException.class,
                    () -> taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                            task.getKey(), "variable", -1, "value"
                    ))
            );
            assertThrows(
                    TaskVariableValueTypeMismatchException.class,
                    () -> taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                            task.getKey(), "variable", Constants.TASK_VARIABLE_VALUE_TYPE_LONG, 42
                    ))
            );
            assertFalse(taskVariableMaintainService.exists(taskVariableKey));
        } finally {
            cleanup();
        }
    }

    @Test
    public void testRemove() throws Exception {
        try {
            prepareTask();
            TaskVariableKey taskVariableKey = registerTaskVariableKey("variable");

            assertThrows(
                    TaskVariableNotExistsException.class,
                    () -> taskVariableOperateHandler.remove(new TaskVariableRemoveInfo(task.getKey(), "variable"))
            );

            taskVariableOperateHandler.upsert(new TaskVariableUpsertInfo(
                    task.getKey(), "variable", Constants.TASK_VARIABLE_VALUE_TYPE_STRING, "value"
            ));
            assertTrue(taskVariableMaintainService.exists(taskVariableKey));

            taskVariableOperateHandler.remove(new TaskVariableRemoveInfo(task.getKey(), "variable"));
            assertFalse(taskVariableMaintainService.exists(taskVariableKey));
        } finally {
            cleanup();
        }
    }

    private void prepareTask() throws Exception {
        section.setKey(sectionMaintainService.insertOrUpdate(section));
        state.getKey().setSectionLongId(section.getKey().getLongId());
        state.setKey(stateMaintainService.insertOrUpdate(state));
        task.setSectionKey(section.getKey());
        task.setCurrentStateKey(state.getKey());
        task.setKey(taskMaintainService.insertOrUpdate(task));
    }

    private void cleanup() throws Exception {
        for (TaskVariableKey taskVariableKey : taskVariableKeys) {
            taskVariableMaintainService.deleteIfExists(taskVariableKey);
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

    private TaskVariableKey registerTaskVariableKey(String taskVariableId) {
        TaskVariableKey taskVariableKey = new TaskVariableKey(task.getKey().getLongId(), taskVariableId);
        taskVariableKeys.add(taskVariableKey);
        return taskVariableKey;
    }

    private void assertVariable(
            TaskVariableKey taskVariableKey, int expectedValueType, Object expectedStoredValue,
            Object expectedInspectedValue
    ) throws Exception {
        assertTrue(taskVariableMaintainService.exists(taskVariableKey));
        TaskVariable taskVariable = taskVariableMaintainService.get(taskVariableKey);
        assertEquals(expectedValueType, taskVariable.getValueType());
        switch (expectedValueType) {
            case Constants.TASK_VARIABLE_VALUE_TYPE_STRING:
                assertEquals(expectedStoredValue, taskVariable.getStringValue());
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_LONG:
                assertEquals(expectedStoredValue, taskVariable.getLongValue());
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DOUBLE:
                assertEquals(expectedStoredValue, taskVariable.getDoubleValue());
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_BOOLEAN:
                assertEquals(expectedStoredValue, taskVariable.getBooleanValue());
                break;
            case Constants.TASK_VARIABLE_VALUE_TYPE_DATE:
                assertEquals(expectedStoredValue, taskVariable.getDateValue());
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }

        TaskVariableInspectResult result = taskVariableOperateHandler.inspect(
                new TaskVariableInspectInfo(task.getKey(), taskVariableKey.getVariableStringId())
        );
        assertNotNull(result);
        assertEquals(expectedValueType, result.getValueType());
        assertEquals(expectedInspectedValue, result.getValue());
    }
}
