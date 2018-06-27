package org.tinyspring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.context.support.ClassPathXmlApplicationContext;
import org.tinyspring.context.support.FileSystemXmlApplicationContext;
import org.tinyspring.service.v1.PetStoreService;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/Users/tyq/IdeaProjects/tinySpring/src/test/resources/petstore-v1.xml");
        PetStoreService service = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(service);
    }
}
