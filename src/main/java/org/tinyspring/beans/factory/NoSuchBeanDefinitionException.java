package org.tinyspring.beans.factory;

import org.tinyspring.beans.BeansException;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public class NoSuchBeanDefinitionException extends BeansException {

    /** Name of the missing bean */
    private String beanName;

    /** Required type of the missing bean */
    private Class<?> beanType;


    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' is defined");
        this.beanName = name;
    }
}
