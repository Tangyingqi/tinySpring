package org.tinyspring.test.v5;

import org.tinyspring.aop.config.AspectInstanceFactory;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/9/11
 */
public class AbstractTest {

    protected BeanFactory getBeanFactory(String configFile){
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
        return  defaultBeanFactory;
    }

    protected Method getAdviceMethod(String methodName) throws Exception{
        return TransactionManager.class.getMethod(methodName);
    }

    protected  AspectInstanceFactory getAspectInstanceFactory(String targetBeanName){
        AspectInstanceFactory factory = new AspectInstanceFactory();
        factory.setAspectBeanName(targetBeanName);
        return factory;
    }
}
