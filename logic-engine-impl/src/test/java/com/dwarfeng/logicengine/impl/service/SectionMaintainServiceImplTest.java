package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
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
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class SectionMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;

    private List<Section> sections;

    @Before
    public void setUp() {
        sections = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Section section = new Section(null, "section-" + UUID.randomUUID(), true, 60000L, "remark");
            sections.add(section);
        }
    }

    @After
    public void tearDown() {
        sections.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {

            for (Section section : sections) {

                section.setKey(sectionMaintainService.insertOrUpdate(section));
                assertTrue(sectionMaintainService.exists(section.getKey()));
                Section testSection = sectionMaintainService.get(section.getKey());
                assertEquals(BeanUtils.describe(section), BeanUtils.describe(testSection));

                section.setRemark("updated");
                sectionMaintainService.update(section);
                testSection = sectionMaintainService.get(section.getKey());
                assertEquals(BeanUtils.describe(section), BeanUtils.describe(testSection));
            }
            for (Section section : sections) {
                sectionMaintainService.delete(section.getKey());
                assertFalse(sectionMaintainService.exists(section.getKey()));
            }
        } finally {
            for (Section section : sections) {
                if (Objects.nonNull(section.getKey())) {
                    sectionMaintainService.deleteIfExists(section.getKey());
                }
            }

        }
    }
}
