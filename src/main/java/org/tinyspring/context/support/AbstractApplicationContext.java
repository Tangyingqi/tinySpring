package org.tinyspring.context.support;

import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.tinyspring.context.ApplicationContext;
import org.tinyspring.core.io.Resource;
import org.tinyspring.utils.ClassUtils;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;

    private ClassLoader classLoader;

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
        //?
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }

    protected abstract Resource getResourceByPath(String path);

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.classLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
}
