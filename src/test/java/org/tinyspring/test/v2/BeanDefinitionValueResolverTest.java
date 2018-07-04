package org.tinyspring.test.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.config.TypedStringValue;
import org.tinyspring.beans.factory.support.BeanDefinitionValueResolver;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.dao.AccountDao;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class BeanDefinitionValueResolverTest {

    private BeanDefinitionValueResolver resolver;

    @Before
    public void setUp(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
        resolver = new BeanDefinitionValueResolver(factory);
    }

    @Test
    public void testResolveRuntimeBeanReference(){

        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue(){

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test",value);

    }
}
