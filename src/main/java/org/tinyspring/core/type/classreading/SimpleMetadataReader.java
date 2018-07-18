package org.tinyspring.core.type.classreading;

import jdk.internal.org.objectweb.asm.ClassReader;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.type.AnnotationMetadata;
import org.tinyspring.core.type.ClassMetadata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
public class SimpleMetadataReader implements MetadataReader{

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream is = new BufferedInputStream(resource.getInputStream());

        ClassReader classReader;

        try {
            classReader = new ClassReader(is);
        }finally {
            is.close();
        }

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor,ClassReader.SKIP_DEBUG);

        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
        this.resource = resource;

    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return classMetadata;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

}
