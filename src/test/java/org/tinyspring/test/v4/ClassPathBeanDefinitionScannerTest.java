package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.context.annotation.ClassPathBeanDefinitionScanner;
import org.tinyspring.context.annotation.ScannedGenericBeanDefinition;
import org.tinyspring.core.annotation.AnnotationAttributes;
import org.tinyspring.core.type.AnnotationMetadata;
import org.tinyspring.stereotype.Component;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testParseScannedBean(){

        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "org.tinyspring.service.v4,org.tinyspring.dao.v4";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);

        String annotation = Component.class.getName();

        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = scd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore",attributes.get("value"));
        }

        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = scd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
        }

        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition scd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = scd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
