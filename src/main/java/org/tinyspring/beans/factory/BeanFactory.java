package org.tinyspring.beans.factory;

import org.tinyspring.beans.BeanDefinition;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
