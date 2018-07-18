package org.tinyspring.core.type;

import org.tinyspring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
public interface AnnotationMetadata extends ClassMetadata{

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
