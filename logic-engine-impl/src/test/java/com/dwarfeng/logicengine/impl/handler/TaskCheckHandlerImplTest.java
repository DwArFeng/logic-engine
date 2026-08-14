package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TaskCheckHandlerImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskCheckHandlerImpl.TaskCheckWorker taskCheckWorker;

    @Test
    public void testExpireCheckAndDieCheck() throws Exception {
        Section section = new Section(null, "section", true, 60000L, "remark");
        Task taskToExpire = new Task(
                null, null, null, Constants.TASK_STATUS_CREATED, new Date(), null, null, null,
                new Date(System.currentTimeMillis() - 1000L), null, null, null, null, "message"
        );
        Task taskToDie = new Task(
                null, null, null, Constants.TASK_STATUS_PROCESSING, new Date(), new Date(), null, null,
                null, new Date(System.currentTimeMillis() - 1000L), null, null, new Date(), "message"
        );
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            taskToExpire.setSectionKey(section.getKey());
            taskToExpire.setKey(taskMaintainService.insertOrUpdate(taskToExpire));
            taskToDie.setSectionKey(section.getKey());
            taskToDie.setKey(taskMaintainService.insertOrUpdate(taskToDie));

            taskCheckWorker.expireCheck();
            taskCheckWorker.dieCheck();

            Task expiredTask = taskMaintainService.get(taskToExpire.getKey());
            assertEquals(Constants.TASK_STATUS_EXPIRED, expiredTask.getStatus());
            assertNotNull(expiredTask.getExpiredDate());
            Task diedTask = taskMaintainService.get(taskToDie.getKey());
            assertEquals(Constants.TASK_STATUS_DIED, diedTask.getStatus());
            assertNotNull(diedTask.getDiedDate());
        } finally {
            if (Objects.nonNull(taskToDie.getKey())) {
                taskMaintainService.deleteIfExists(taskToDie.getKey());
            }
            if (Objects.nonNull(taskToExpire.getKey())) {
                taskMaintainService.deleteIfExists(taskToExpire.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
