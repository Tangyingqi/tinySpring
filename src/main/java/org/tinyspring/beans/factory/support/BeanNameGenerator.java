package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition beanDefinition,BeanDefinitionRegistry registry);
}
