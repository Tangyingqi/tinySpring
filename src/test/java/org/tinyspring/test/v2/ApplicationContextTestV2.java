package org.tinyspring.test.v2;

import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.dao.AccountDao;
import org.tinyspring.dao.ItemDao;
import org.tinyspring.service.v2.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author tangyingqi
 * @date 2018/7/2
 */
public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext contex = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) contex.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());

        assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        assertTrue(petStoreService.getItemDao() instanceof ItemDao);

        assertEquals("tangyingqi",petStoreService.getOwner());
        assertEquals(1,petStoreService.getVersion());
    }
}


