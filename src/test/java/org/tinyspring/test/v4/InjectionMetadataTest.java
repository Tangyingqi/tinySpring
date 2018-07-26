package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.factory.annotation.AutowiredFieldElement;
import org.tinyspring.beans.factory.annotation.InjectionElement;
import org.tinyspring.beans.factory.annotation.InjectionMetadata;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.dao.v4.AccountDao;
import org.tinyspring.dao.v4.ItemDao;
import org.tinyspring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author tangyingqi
 * @date 2018/7/26
 */
public class InjectionMetadataTest {

    @Test
    public void testInjection() throws NoSuchFieldException {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Class<?> clz = PetStoreService.class;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();

        {
            Field f = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElement);
        }
        {
            Field f = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElement);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz,elements);

        PetStoreService petStore = new PetStoreService();

        metadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }
}
