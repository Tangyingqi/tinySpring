package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author tangyingqi
 * @date 2018/9/16
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition bd);
}
