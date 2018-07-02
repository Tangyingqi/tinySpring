package org.tinyspring.test.v2;

import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.PropertyValue;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.support.ClassPathResource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author tangyingqi
 * @date 2018/7/2
 */
public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();

        assertEquals(2, pvs.size());

        {
            PropertyValue pv = getPropertyValue("accountDao",pvs);

            assertNotNull(pv);

            assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = getPropertyValue("itemDao",pvs);

            assertNotNull(pv);

            assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }


    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
        for (PropertyValue pv : pvs){
            if (pv.getName().equals(name)){
                return pv;
            }
        }
        return null;
    }
}
