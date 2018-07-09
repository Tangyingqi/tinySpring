package org.tinyspring.test.v3;

import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.ConstructorArgument;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.config.TypedStringValue;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author tangyingqi
 * @date 2018/7/9
 */
public class BeanDefinitionTestV3 {

    @Test
    public void testConstructorArgument(){

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        assertEquals("org.tinyspring.service.v3.PetStoreService",bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        assertEquals(3,valueHolders.size());

        RuntimeBeanReference rf1 = (RuntimeBeanReference)valueHolders.get(0).getValue();
        assertEquals("accountDao",rf1.getBeanName());
        RuntimeBeanReference rf2 = (RuntimeBeanReference)valueHolders.get(1).getValue();
        assertEquals("itemDao",rf2.getBeanName());

        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        assertEquals("1",strValue.getValue());

    }
}
