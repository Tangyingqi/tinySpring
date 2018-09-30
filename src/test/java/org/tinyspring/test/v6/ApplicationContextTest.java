package org.tinyspring.test.v6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.service.v6.IPetStoreService;
import org.tinyspring.util.MessageTracker;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/9/30
 */
public class ApplicationContextTest {

    @Test
    public void testGetBeanProperty(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v6.xml");
        IPetStoreService petStore = (IPetStoreService) applicationContext.getBean("petStore");

        petStore.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();

        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

    @Before
    public void setUp(){
        MessageTracker.cleanMsg();
    }
}
