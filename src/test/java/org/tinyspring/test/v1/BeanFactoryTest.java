package org.tinyspring.test.v1;

import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.BeanDefinitionStoreException;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.service.v1.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean(){

        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.tinyspring.service.v1.PetStoreService",bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean(){

        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");
        }catch (BeanCreationException e){
            return;
        }
        fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXml(){
        try {
            new DefaultBeanFactory("xxxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        fail("expect BeanDefinitionStoreException");
    }
}
