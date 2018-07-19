package org.tinyspring.beans.factory.annotation;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.core.type.AnnotationMetadata;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();
}
