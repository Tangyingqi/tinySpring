package org.tinyspring.test.v3;

import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.support.ConstructorResolver;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.service.v3.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author tangyingqi
 * @date 2018/7/10
 */
public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService petStore = (PetStoreService)resolver.autowireConstructor(bd);

        assertEquals(1,petStore.getVersion());
        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
    }
}
