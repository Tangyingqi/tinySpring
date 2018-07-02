package org.tinyspring.beans.factory.config;

/**
 * @author tangyingqi
 * @date 2018/7/2
 */
public class RuntimeBeanReference {

    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
