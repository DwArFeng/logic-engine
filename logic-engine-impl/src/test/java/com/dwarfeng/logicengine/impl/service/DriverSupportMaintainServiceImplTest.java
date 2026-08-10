package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.DriverSupport;
import com.dwarfeng.logicengine.stack.service.DriverSupportMaintainService;
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
public class DriverSupportMaintainServiceImplTest {

    @Autowired
    private DriverSupportMaintainService driverSupportMaintainService;

    private List<DriverSupport> driverSupports;

    @Before
    public void setUp() {
        driverSupports = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DriverSupport driverSupport = new DriverSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            driverSupports.add(driverSupport);
        }
    }

    @After
    public void tearDown() {
        driverSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {

            for (DriverSupport driverSupport : driverSupports) {

                driverSupport.setKey(driverSupportMaintainService.insertOrUpdate(driverSupport));
                assertTrue(driverSupportMaintainService.exists(driverSupport.getKey()));
                DriverSupport testDriverSupport = driverSupportMaintainService.get(driverSupport.getKey());
                assertEquals(BeanUtils.describe(driverSupport), BeanUtils.describe(testDriverSupport));

                driverSupport.setDescription("updated");
                driverSupportMaintainService.update(driverSupport);
                testDriverSupport = driverSupportMaintainService.get(driverSupport.getKey());
                assertEquals(BeanUtils.describe(driverSupport), BeanUtils.describe(testDriverSupport));
            }
            for (DriverSupport driverSupport : driverSupports) {
                driverSupportMaintainService.delete(driverSupport.getKey());
                assertFalse(driverSupportMaintainService.exists(driverSupport.getKey()));
            }
        } finally {
            for (DriverSupport driverSupport : driverSupports) {
                if (Objects.nonNull(driverSupport.getKey())) {
                    driverSupportMaintainService.deleteIfExists(driverSupport.getKey());
                }
            }
        }
    }
}
