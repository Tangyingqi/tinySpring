package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.aop.config.MethodLocatingFactory;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public class MethodLocatingFactoryTest {

    @Test
    public void testGetMethod() throws NoSuchMethodException {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petstore-v5.xml");
        reader.loadBeanDefinition(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(beanFactory);

        Method m = methodLocatingFactory.getObject();

        Assert.assertEquals(TransactionManager.class, m.getDeclaringClass());
        Assert.assertEquals(m,TransactionManager.class.getMethod("start"));
    }
}
