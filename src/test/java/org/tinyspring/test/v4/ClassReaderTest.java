package org.tinyspring.test.v4;

import jdk.internal.org.objectweb.asm.ClassReader;
import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.core.annotation.AnnotationAttributes;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.tinyspring.core.type.classreading.ClassMetadataReadingVisitor;

import java.io.IOException;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
public class ClassReaderTest {

    @Test
    public void testGetClassMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinyspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.tinyspring.service.v4.PetStoreService",visitor.getClassName());
        Assert.assertEquals("java.lang.Object",visitor.getSuperClassName());
        Assert.assertEquals(0,visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinyspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        String annotation = "org.tinyspring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore",attributes.get("value"));

    }
}
