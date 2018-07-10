package org.tinyspring.test.v3;

import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.dao.AccountDao;
import org.tinyspring.dao.ItemDao;
import org.tinyspring.service.v3.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author tangyingqi
 * @date 2018/7/9
 */
public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
        assertEquals(1,petStoreService.getVersion());

        assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        assertTrue(petStoreService.getItemDao() instanceof ItemDao);

    }
}
