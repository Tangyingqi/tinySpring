package org.tinyspring.core.type.classreading;

import org.tinyspring.core.io.Resource;
import org.tinyspring.core.type.AnnotationMetadata;
import org.tinyspring.core.type.ClassMetadata;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
