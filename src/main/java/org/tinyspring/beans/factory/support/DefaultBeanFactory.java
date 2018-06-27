package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;
import org.tinyspring.utils.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory,BeanDefinitionRegistry {


    private ClassLoader classLoader;

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();


    public BeanDefinition getBeanDefinition(String beanID) {
        
        return beanDefinitionMap.get(beanID);
    }

    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId,bd);
    }

    public Object getBean(String beanID) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        if (beanDefinition == null) {
            return null;
        }

        if (beanDefinition.isSingleton()){
            Object bean = this.getSingleton(beanID);
            if (bean == null){
                bean = createBean(beanDefinition);
                this.registrySingleton(beanID,bean);
            }
            return bean;
        }
        return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition) {

        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for"+ beanClassName+"failed",e);
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.classLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {

        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
}
