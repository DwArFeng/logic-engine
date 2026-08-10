package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.logicengine.stack.service.PerformerSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
public class PerformerSupportMaintainServiceImplTest {

    @Autowired
    private PerformerSupportMaintainService performerSupportMaintainService;

    private List<PerformerSupport> performerSupports;

    @Before
    public void setUp() {
        performerSupports = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PerformerSupport performerSupport = new PerformerSupport(
                    new StringIdKey("performer-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            performerSupports.add(performerSupport);
        }
    }

    @After
    public void tearDown() {
        performerSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {

            for (PerformerSupport performerSupport : performerSupports) {

                performerSupport.setKey(performerSupportMaintainService.insertOrUpdate(performerSupport));
                assertTrue(performerSupportMaintainService.exists(performerSupport.getKey()));
                PerformerSupport testPerformerSupport = performerSupportMaintainService.get(performerSupport.getKey());
                assertEquals(BeanUtils.describe(performerSupport), BeanUtils.describe(testPerformerSupport));

                performerSupport.setDescription("updated");
                performerSupportMaintainService.update(performerSupport);
                testPerformerSupport = performerSupportMaintainService.get(performerSupport.getKey());
                assertEquals(BeanUtils.describe(performerSupport), BeanUtils.describe(testPerformerSupport));
            }
            for (PerformerSupport performerSupport : performerSupports) {
                performerSupportMaintainService.delete(performerSupport.getKey());
                assertFalse(performerSupportMaintainService.exists(performerSupport.getKey()));
            }
        } finally {
            for (PerformerSupport performerSupport : performerSupports) {
                if (Objects.nonNull(performerSupport.getKey())) {
                    performerSupportMaintainService.deleteIfExists(performerSupport.getKey());
                }
            }

        }
    }
}
