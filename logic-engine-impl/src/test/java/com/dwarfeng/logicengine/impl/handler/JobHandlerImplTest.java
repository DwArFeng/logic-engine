package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.impl.handler.guarder.groovy.GroovyGuarderRegistry;
import com.dwarfeng.logicengine.impl.handler.performer.groovy.GroovyPerformerRegistry;
import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.entity.*;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.handler.JobLocalCacheHandler;
import com.dwarfeng.logicengine.stack.service.*;
import com.dwarfeng.logicengine.stack.struct.JobLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class JobHandlerImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private StateMaintainService stateMaintainService;
    @Autowired
    private GuarderInfoMaintainService guarderInfoMaintainService;
    @Autowired
    private PerformerInfoMaintainService performerInfoMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskVariableMaintainService taskVariableMaintainService;
    @Autowired
    private JobLocalCacheHandler jobLocalCacheHandler;
    @Autowired
    private JobQosService jobQosService;

    @Test
    public void testExecuteToTerminalStateAndLocalCacheLifecycle() throws Exception {
        Section section = new Section(null, "job-test", true, 60000L, "job-test");
        State initialState = null;
        State terminalState = null;
        GuarderInfo guarderInfo = null;
        PerformerInfo performerInfo = null;
        LongIdKey taskKey = null;
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            initialState = new State(
                    new StateKey(section.getKey().getLongId(), "initial"), "initial",
                    Constants.STATE_TYPE_INITIAL, 0L, 1L, null
            );
            initialState.setKey(stateMaintainService.insertOrUpdate(initialState));
            terminalState = new State(
                    new StateKey(section.getKey().getLongId(), "terminal"), "terminal",
                    Constants.STATE_TYPE_TERMINAL, 0L, 1L, null
            );
            terminalState.setKey(stateMaintainService.insertOrUpdate(terminalState));

            String guarderScript = "import com.dwarfeng.logicengine.impl.handler.guarder.groovy.Processor\n" +
                    "import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo\n" +
                    "import com.dwarfeng.logicengine.stack.handler.Guarder\n" +
                    "class JobTestGuarderProcessor implements Processor {\n" +
                    "  boolean test(Guarder.Context context) {\n" +
                    "    context.upsertTaskVariable(new TaskVariableUpsertInfo(" +
                    "context.task.key, 'guard', 0, 'checked'))\n" +
                    "    return true\n" +
                    "  }\n" +
                    "}\n";
            guarderInfo = new GuarderInfo(
                    null, section.getKey(), initialState.getKey(), terminalState.getKey(), 0, true,
                    GroovyGuarderRegistry.GUARDER_TYPE, guarderScript, null
            );
            guarderInfo.setKey(guarderInfoMaintainService.insertOrUpdate(guarderInfo));

            String script = "import com.dwarfeng.logicengine.impl.handler.performer.groovy.Processor\n" +
                    "import com.dwarfeng.logicengine.stack.bean.dto.TaskVariableUpsertInfo\n" +
                    "import com.dwarfeng.logicengine.stack.handler.Performer\n" +
                    "class JobTestProcessor implements Processor {\n" +
                    "  void execute(Performer.Context context) {\n" +
                    "    context.upsertTaskVariable(new TaskVariableUpsertInfo(" +
                    "context.task.key, 'result', 0, 'executed'))\n" +
                    "  }\n" +
                    "}\n";
            performerInfo = new PerformerInfo(
                    null, section.getKey(), initialState.getKey(), terminalState.getKey(), 0, true,
                    GroovyPerformerRegistry.PERFORMER_TYPE, script, null
            );
            performerInfo.setKey(performerInfoMaintainService.insertOrUpdate(performerInfo));

            JobLocalCache firstCache = jobLocalCacheHandler.get(section.getKey());
            assertSame(firstCache, jobLocalCacheHandler.get(section.getKey()));
            jobLocalCacheHandler.remove(section.getKey());
            assertNotSame(firstCache, jobLocalCacheHandler.get(section.getKey()));

            jobQosService.execute(section.getKey());

            Task task = taskMaintainService.lookupFirst(TaskMaintainService.CHILD_FOR_SECTION,
                    new Object[]{section.getKey()});
            assertNotNull(task);
            taskKey = task.getKey();
            assertEquals(Constants.TASK_STATUS_FINISHED, task.getStatus());
            assertEquals(terminalState.getKey(), task.getCurrentStateKey());
            assertNotNull(task.getStartedDate());
            assertNotNull(task.getEndedDate());
            TaskVariable taskVariable = taskVariableMaintainService.get(
                    new TaskVariableKey(taskKey.getLongId(), "result")
            );
            assertEquals("executed", taskVariable.getStringValue());
            TaskVariable guardTaskVariable = taskVariableMaintainService.get(
                    new TaskVariableKey(taskKey.getLongId(), "guard")
            );
            assertEquals("checked", guardTaskVariable.getStringValue());
        } finally {
            if (Objects.nonNull(taskKey)) {
                taskMaintainService.deleteIfExists(taskKey);
            }
            if (Objects.nonNull(performerInfo) && Objects.nonNull(performerInfo.getKey())) {
                performerInfoMaintainService.deleteIfExists(performerInfo.getKey());
            }
            if (Objects.nonNull(guarderInfo) && Objects.nonNull(guarderInfo.getKey())) {
                guarderInfoMaintainService.deleteIfExists(guarderInfo.getKey());
            }
            if (Objects.nonNull(initialState) && Objects.nonNull(initialState.getKey())) {
                stateMaintainService.deleteIfExists(initialState.getKey());
            }
            if (Objects.nonNull(terminalState) && Objects.nonNull(terminalState.getKey())) {
                stateMaintainService.deleteIfExists(terminalState.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                jobLocalCacheHandler.remove(section.getKey());
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testExecuteWithInvalidSection() {
        try {
            jobQosService.execute(new LongIdKey(-1L));
            fail("执行不存在的部件时应抛出服务异常");
        } catch (ServiceException ignored) {
        }
    }
}
