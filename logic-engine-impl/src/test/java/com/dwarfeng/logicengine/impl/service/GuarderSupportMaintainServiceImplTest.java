package com.dwarfeng.logicengine.impl.service;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.logicengine.stack.service.GuarderSupportMaintainService;
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
public class GuarderSupportMaintainServiceImplTest {

    @Autowired
    private GuarderSupportMaintainService guarderSupportMaintainService;

    private List<GuarderSupport> guarderSupports;

    @Before
    public void setUp() {
        guarderSupports = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GuarderSupport guarderSupport = new GuarderSupport(
                    new StringIdKey("guarder-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            guarderSupports.add(guarderSupport);
        }
    }

    @After
    public void tearDown() {
        guarderSupports.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {

            for (GuarderSupport guarderSupport : guarderSupports) {

                guarderSupport.setKey(guarderSupportMaintainService.insertOrUpdate(guarderSupport));
                assertTrue(guarderSupportMaintainService.exists(guarderSupport.getKey()));
                GuarderSupport testGuarderSupport = guarderSupportMaintainService.get(guarderSupport.getKey());
                assertEquals(BeanUtils.describe(guarderSupport), BeanUtils.describe(testGuarderSupport));

                guarderSupport.setDescription("updated");
                guarderSupportMaintainService.update(guarderSupport);
                testGuarderSupport = guarderSupportMaintainService.get(guarderSupport.getKey());
                assertEquals(BeanUtils.describe(guarderSupport), BeanUtils.describe(testGuarderSupport));
            }
            for (GuarderSupport guarderSupport : guarderSupports) {
                guarderSupportMaintainService.delete(guarderSupport.getKey());
                assertFalse(guarderSupportMaintainService.exists(guarderSupport.getKey()));
            }
        } finally {
            for (GuarderSupport guarderSupport : guarderSupports) {
                if (Objects.nonNull(guarderSupport.getKey())) {
                    guarderSupportMaintainService.deleteIfExists(guarderSupport.getKey());
                }
            }

        }
    }
}
