package org.tinyspring.beans.factory.config;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
