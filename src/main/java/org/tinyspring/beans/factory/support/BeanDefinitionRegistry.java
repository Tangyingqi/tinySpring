package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);

    /**
     *
     * @param beanId
     * @param bd
     */
    void registerBeanDefinition(String beanId, BeanDefinition bd);
}
