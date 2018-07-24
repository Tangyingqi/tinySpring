package org.tinyspring.beans.factory.config;

import org.tinyspring.beans.factory.BeanFactory;

/**
 * @author tangyingqi
 * @date 2018/7/24
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}
