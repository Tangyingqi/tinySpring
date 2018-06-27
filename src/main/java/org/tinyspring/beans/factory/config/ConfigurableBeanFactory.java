package org.tinyspring.beans.factory.config;

import org.tinyspring.beans.factory.BeanFactory;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
