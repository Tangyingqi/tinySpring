package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.factory.config.DependencyDescriptor;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.dao.v4.AccountDao;
import org.tinyspring.service.v4.PetStoreService;

import java.lang.reflect.Field;

/**
 * @author tangyingqi
 * @date 2018/7/24
 */
public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws NoSuchFieldException {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f,true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);

    }
}
