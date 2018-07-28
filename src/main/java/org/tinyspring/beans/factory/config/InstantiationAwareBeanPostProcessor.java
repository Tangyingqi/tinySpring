package org.tinyspring.beans.factory.config;

/**
 * @author tangyingqi
 * @date 2018/7/28
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object beforeInstantiation(Class<?> beanClass,String beanName);

    boolean afterInstantiation(Object bean,String beanName);

    void postProcessPropertyValues(Object bean,String beanName);
}
