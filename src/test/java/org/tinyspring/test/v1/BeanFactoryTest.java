package org.tinyspring.test.v1;

import org.junit.Before;
import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.BeanDefinitionStoreException;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.service.v1.PetStoreService;

import static org.junit.Assert.*;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;


    @Before
    public void setUP(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }


    @Test
    public void testGetBean(){

        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());

        assertFalse(bd.isProtoType());

        assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());

        assertEquals("org.tinyspring.service.v1.PetStoreService",bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
        assertEquals(petStore, petStore1);
    }

    @Test
    public void testInvalidBean(){

        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));

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
            reader.loadBeanDefinition(new ClassPathResource("xxxx.xml"));
        }catch (BeanDefinitionStoreException e){
            return;
        }
        fail("expect BeanDefinitionStoreException");
    }
}
