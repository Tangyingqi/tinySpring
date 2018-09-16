package org.tinyspring.aop.config;

import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.beans.factory.BeanFactoryAware;
import org.tinyspring.utils.StringUtils;

/**
 * @author tangyingqi
 * @date 2018/9/11
 */
public class AspectInstanceFactory implements BeanFactoryAware {

    private String aspectBeanName;

    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {

        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)){
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance(){
        return this.beanFactory.getBean(aspectBeanName);
    }
}
