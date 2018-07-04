package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.config.TypedStringValue;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            return factory.getBean(refName);
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("the value" + value + "has not implemented");
        }
    }
}
