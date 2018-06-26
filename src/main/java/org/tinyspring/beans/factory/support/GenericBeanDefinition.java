package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }
}
