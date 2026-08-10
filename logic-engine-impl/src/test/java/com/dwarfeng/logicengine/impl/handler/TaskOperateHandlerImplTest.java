package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.*;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.logicengine.stack.handler.TaskOperateHandler;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TaskOperateHandlerImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskOperateHandler taskOperateHandler;

    @Test
    public void testCreateStartBeatChangeStateUpdateModalFinish() throws Exception {
        Section section = new Section(null, "section", true, 60000L, "remark");
        LongIdKey taskKey = null;
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));

            taskKey = taskOperateHandler.create(new TaskCreateInfo(section.getKey())).getTaskKey();
            Task task = taskMaintainService.get(taskKey);
            assertEquals(Constants.TASK_STATUS_CREATED, task.getStatus());
            assertNull(task.getAnchorMessage());
            assertNotNull(task.getCreatedDate());
            assertNotNull(task.getShouldExpireDate());
            assertEquals(
                    section.getExpireTimeout(),
                    task.getShouldExpireDate().getTime() - task.getCreatedDate().getTime()
            );
            long shouldExpireDate = task.getShouldExpireDate().getTime();

            taskOperateHandler.start(new TaskStartInfo(taskKey));
            task = taskMaintainService.get(taskKey);
            assertEquals(Constants.TASK_STATUS_PROCESSING, task.getStatus());
            assertNull(task.getCurrentStateKey());
            assertNull(task.getAnchorMessage());
            assertNotNull(task.getStartedDate());
            assertNotNull(task.getShouldDieDate());

            long oldShouldDieDate = task.getShouldDieDate().getTime();
            taskOperateHandler.beat(new TaskBeatInfo(taskKey));
            task = taskMaintainService.get(taskKey);
            assertTrue(task.getShouldDieDate().getTime() >= oldShouldDieDate);
            assertEquals(shouldExpireDate, task.getShouldExpireDate().getTime());

            taskOperateHandler.updateModal(new TaskUpdateModalInfo(taskKey, "update modal"));
            task = taskMaintainService.get(taskKey);
            assertEquals("update modal", task.getAnchorMessage());

            StateKey stateKey = new StateKey(section.getKey().getLongId(), "state");
            taskOperateHandler.changeState(new TaskChangeStateInfo(taskKey, stateKey));
            task = taskMaintainService.get(taskKey);
            assertEquals(stateKey, task.getCurrentStateKey());
            assertNotNull(task.getStateChangedDate());
            assertEquals("update modal", task.getAnchorMessage());

            taskOperateHandler.finish(new TaskFinishInfo(taskKey));
            task = taskMaintainService.get(taskKey);
            assertEquals(Constants.TASK_STATUS_FINISHED, task.getStatus());
            assertEquals("update modal", task.getAnchorMessage());
            assertNotNull(task.getEndedDate());
            assertNotNull(task.getDuration());
        } finally {
            if (Objects.nonNull(taskKey)) {
                taskMaintainService.deleteIfExists(taskKey);
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }

    @Test
    public void testFailExpireAndDie() throws Exception {
        Section section = new Section(null, "section", true, 60000L, "remark");
        LongIdKey failedTaskKey = null;
        LongIdKey expiredTaskKey = null;
        LongIdKey diedTaskKey = null;
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));

            failedTaskKey = taskOperateHandler.create(new TaskCreateInfo(section.getKey())).getTaskKey();
            taskOperateHandler.fail(new TaskFailInfo(failedTaskKey));
            Task failedTask = taskMaintainService.get(failedTaskKey);
            assertEquals(Constants.TASK_STATUS_FAILED, failedTask.getStatus());
            assertNotNull(failedTask.getEndedDate());
            assertNotNull(failedTask.getDuration());

            expiredTaskKey = taskOperateHandler.create(new TaskCreateInfo(section.getKey())).getTaskKey();
            taskOperateHandler.expire(new TaskExpireInfo(expiredTaskKey));
            Task expiredTask = taskMaintainService.get(expiredTaskKey);
            assertEquals(Constants.TASK_STATUS_EXPIRED, expiredTask.getStatus());
            assertNotNull(expiredTask.getExpiredDate());
            assertNotNull(expiredTask.getEndedDate());
            assertNotNull(expiredTask.getDuration());

            diedTaskKey = taskOperateHandler.create(new TaskCreateInfo(section.getKey())).getTaskKey();
            taskOperateHandler.start(new TaskStartInfo(diedTaskKey));
            taskOperateHandler.die(new TaskDieInfo(diedTaskKey));
            Task diedTask = taskMaintainService.get(diedTaskKey);
            assertEquals(Constants.TASK_STATUS_DIED, diedTask.getStatus());
            assertNotNull(diedTask.getDiedDate());
            assertNotNull(diedTask.getEndedDate());
            assertNotNull(diedTask.getDuration());
        } finally {
            if (Objects.nonNull(diedTaskKey)) {
                taskMaintainService.deleteIfExists(diedTaskKey);
            }
            if (Objects.nonNull(expiredTaskKey)) {
                taskMaintainService.deleteIfExists(expiredTaskKey);
            }
            if (Objects.nonNull(failedTaskKey)) {
                taskMaintainService.deleteIfExists(failedTaskKey);
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
