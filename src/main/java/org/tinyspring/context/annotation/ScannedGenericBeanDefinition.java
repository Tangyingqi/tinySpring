package org.tinyspring.context.annotation;

import org.tinyspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.tinyspring.beans.factory.support.GenericBeanDefinition;
import org.tinyspring.core.type.AnnotationMetadata;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(metadata.getClassName());
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
