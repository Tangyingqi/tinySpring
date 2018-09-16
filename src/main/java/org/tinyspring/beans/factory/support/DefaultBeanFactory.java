package org.tinyspring.beans.factory.support;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.PropertyValue;
import org.tinyspring.beans.SimpleTypeConverter;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.BeanFactoryAware;
import org.tinyspring.beans.factory.NoSuchBeanDefinitionException;
import org.tinyspring.beans.factory.config.BeanPostProcessor;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;
import org.tinyspring.beans.factory.config.DependencyDescriptor;
import org.tinyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.tinyspring.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class DefaultBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry {


    private ClassLoader classLoader;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();


    @Override
    public BeanDefinition getBeanDefinition(String beanID) {

        return beanDefinitionMap.get(beanID);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId, bd);
    }

    @Override
    public List<Object> getBeansByType(Class<?> type){
        List<Object> result = new ArrayList<>();
        List<String> beanIDs = this.getBeanIDsByType(type);
        for (String beanID : beanIDs){
            result.add(this.getBean(beanID));
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type){
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionMap.keySet()){
            if (type.isAssignableFrom(this.getType(beanName))){
                result.add(beanName);
            }
        }
        return result;
    }

    @Override
    public Object getBean(String beanID) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        if (beanDefinition == null) {
            return null;
        }

        if (beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(beanDefinition);
                this.registrySingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(beanDefinition);

    }

    @Override
    public Class<?> getType(String name) {
        BeanDefinition bd = getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }

    @Override
    protected Object createBean(BeanDefinition bd) {
        //创建实例
        Object bean = instantiateBean(bd);

        populateBean(bd, bean);

        bean = initializeBean(bd,bean);

        return bean;
    }

    private Object initializeBean(BeanDefinition bd, Object bean) {
        invokeAwareMethods(bean);
        if (!bd.isSynthetic()){
            return applyBeanPostProcessorsAfterInitialization(bean,bd.getID());
        }
        return bean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {

        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()){
            result = beanPostProcessor.afterInitialization(result,beanName);
        }
        return result;
    }

    private void invokeAwareMethods(Object bean) {
        if (bean instanceof BeanFactoryAware){
            ((BeanFactoryAware)bean).setBeanFactory(this);
        }
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {

        if (beanDefinition.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(beanDefinition);
        }

        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for" + beanClassName + "failed", e);
        }
    }

    protected void populateBean(BeanDefinition bd, Object bean) {

        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getID());
            }
        }

        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : descriptors) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class[" + bd.getBeanClassName() + "]", e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.classLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {

        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : beanDefinitionMap.values()) {
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        } else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }
    }
}
