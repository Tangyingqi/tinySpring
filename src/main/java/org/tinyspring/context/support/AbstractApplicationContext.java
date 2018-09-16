package org.tinyspring.context.support;

import org.tinyspring.aop.aspectj.AspectJAutoProxyCreator;
import org.tinyspring.beans.factory.annotation.AutowireAnnotationProcessor;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.ClassUtils;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;

    private ClassLoader classLoader;

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    public AbstractApplicationContext(String configFile) {
        this(configFile,ClassUtils.getDefaultClassLoader());
    }

    public AbstractApplicationContext(String configFile,ClassLoader cl) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
        factory.setBeanClassLoader(cl);
        registerBeanPostProcessors(factory);
    }

    protected abstract Resource getResourceByPath(String path);

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.classLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory){
        {
            AutowireAnnotationProcessor postProcessor = new AutowireAnnotationProcessor();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }

        {
            AspectJAutoProxyCreator postProcessor = new AspectJAutoProxyCreator();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }

    @Override
    public Class<?> getType(String name) {
        return factory.getType(name);
    }

    @Override
    public List<Object> getBeansByType(Class<?> type) {
        return factory.getBeansByType(type);
    }
}
