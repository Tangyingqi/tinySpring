package org.tinyspring.beans.factory.config;

/**
 * @author tangyingqi
 * @date 2018/7/28
 */
public interface BeanPostProcessor {

    Object beforeInitialization(Object bean,String beanName);

    Object afterInitialization(Object bean,String beanName);
}
