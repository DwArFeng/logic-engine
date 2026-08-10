package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.service.DriverInfoMaintainService;
import com.dwarfeng.logicengine.stack.service.SectionMaintainService;
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
public class DriverInfoMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private DriverInfoMaintainService driverInfoMaintainService;

    private Section section;
    private List<DriverInfo> driverInfos;

    @Before
    public void setUp() {
        section = new Section(null, "section", true, 60000L, "remark");
        driverInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            driverInfos.add(new DriverInfo(null, null, true, "driver", "{}", "remark"));
        }
    }

    @After
    public void tearDown() {
        section = null;
        driverInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            section.setKey(sectionMaintainService.insertOrUpdate(section));
            for (DriverInfo driverInfo : driverInfos) {
                driverInfo.setSectionKey(section.getKey());
                driverInfo.setKey(driverInfoMaintainService.insertOrUpdate(driverInfo));
                assertTrue(driverInfoMaintainService.exists(driverInfo.getKey()));
                DriverInfo testDriverInfo = driverInfoMaintainService.get(driverInfo.getKey());
                assertEquals(BeanUtils.describe(driverInfo), BeanUtils.describe(testDriverInfo));

                driverInfo.setRemark("updated");
                driverInfoMaintainService.update(driverInfo);
                testDriverInfo = driverInfoMaintainService.get(driverInfo.getKey());
                assertEquals(BeanUtils.describe(driverInfo), BeanUtils.describe(testDriverInfo));
            }
            for (DriverInfo driverInfo : driverInfos) {
                driverInfoMaintainService.delete(driverInfo.getKey());
                assertFalse(driverInfoMaintainService.exists(driverInfo.getKey()));
            }
        } finally {
            for (DriverInfo driverInfo : driverInfos) {
                if (Objects.nonNull(driverInfo.getKey())) {
                    driverInfoMaintainService.deleteIfExists(driverInfo.getKey());
                }
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
            for (DriverInfo driverInfo : driverInfos) {
                driverInfo.setSectionKey(section.getKey());
                driverInfo.setKey(driverInfoMaintainService.insertOrUpdate(driverInfo));
            }
            assertEquals(
                    driverInfos.size(),
                    driverInfoMaintainService.lookupAsList(
                            DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );

            sectionMaintainService.deleteIfExists(section.getKey());

            assertEquals(
                    0,
                    driverInfoMaintainService.lookupAsList(
                            DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{section.getKey()}
                    ).size()
            );
            for (DriverInfo driverInfo : driverInfos) {
                assertFalse(driverInfoMaintainService.exists(driverInfo.getKey()));
            }
        } finally {
            for (DriverInfo driverInfo : driverInfos) {
                if (Objects.nonNull(driverInfo.getKey())) {
                    driverInfoMaintainService.deleteIfExists(driverInfo.getKey());
                }
            }
            if (Objects.nonNull(section.getKey())) {
                sectionMaintainService.deleteIfExists(section.getKey());
            }
        }
    }
}
