package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.PropertyValue;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;
import org.tinyspring.utils.ClassUtils;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
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

    private Object createBean(BeanDefinition bd){
        //创建实例
        Object bean = instantiateBean(bd);

        populateBean(bd,bean);

        return bean;
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {

        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for"+ beanClassName+"failed",e);
        }
    }

    protected void populateBean(BeanDefinition bd,Object bean){

        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()){
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : descriptors){
                    if (pd.getName().equals(propertyName)){
                        pd.getWriteMethod().invoke(bean,resolvedValue);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class["+ bd.getBeanClassName() +"]",e);
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.classLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {

        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
}
