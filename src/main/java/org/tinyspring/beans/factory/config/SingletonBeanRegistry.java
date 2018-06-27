package org.tinyspring.beans.factory.config;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public interface SingletonBeanRegistry {

    void registrySingleton(String beanName,Object singletonObject);

    Object getSingleton(String beanName);
}
