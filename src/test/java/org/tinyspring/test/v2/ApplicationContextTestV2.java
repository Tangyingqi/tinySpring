package org.tinyspring.test.v2;

import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.service.v2.PetStoreService;

import static org.junit.Assert.assertNotNull;

/**
 * @author tangyingqi
 * @date 2018/7/2
 */
public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty(){
        ApplicationContext contex = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) contex.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
    }
}
