package com.dwarfeng.logicengine.impl.handler;

import com.dwarfeng.logicengine.sdk.util.Constants;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.logicengine.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.logicengine.stack.handler.TaskEventOperateHandler;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskEventMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
public class TaskEventOperateHandlerImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskEventMaintainService taskEventMaintainService;
    @Autowired
    private TaskEventOperateHandler taskEventOperateHandler;

    @Test
    public void testCreate() throws Exception {
        Section section = new Section(null, "section", true, 60000L, "remark");
        Task task = new Task(
                null, null, null, Constants.TASK_STATUS_CREATED, new Date(), null, null, null,
                new Date(System.currentTimeMillis() + 60000L), null, null, null, null, "message"
        );
        LongIdKey taskEventKey = null;
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            task.setSectionKey(section.getKey());
            task.setKey(taskMaintainService.insertOrUpdate(task));

            Date happenedDate = new Date(1000L);
            TaskEventCreateResult result = taskEventOperateHandler.create(
                    new TaskEventCreateInfo(task.getKey(), happenedDate, "message")
            );
            taskEventKey = result.getTaskEventKey();
            TaskEvent taskEvent = taskEventMaintainService.get(taskEventKey);
            assertNotNull(taskEvent);
            assertEquals(task.getKey(), taskEvent.getTaskKey());
            assertEquals(happenedDate, taskEvent.getHappenedDate());
            assertEquals("message", taskEvent.getMessage());
        } finally {
            if (Objects.nonNull(taskEventKey)) {
                taskEventMaintainService.deleteIfExists(taskEventKey);
            }
            if (Objects.nonNull(task.getKey())) {
                taskMaintainService.deleteIfExists(task.getKey());
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
