package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.service.v5.PetStoreService;
import org.tinyspring.util.MessageTracker;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/7/30
 */
public class ApplicationContextTest {

    @Before
    public void setUp(){
        MessageTracker.cleanMsg();
    }

    @Test
    public void testPlaceHolder(){

        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v5.xml");
        PetStoreService petStore = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        petStore.placeOrder();

        List<String> messages = MessageTracker.getMsgs();

        Assert.assertEquals(3,messages.size());
        Assert.assertEquals("start tx",messages.get(0));
        Assert.assertEquals("place holder",messages.get(1));
        Assert.assertEquals("end tx",messages.get(2));
    }
}
