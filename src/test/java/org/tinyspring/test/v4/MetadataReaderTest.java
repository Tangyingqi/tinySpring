package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.core.annotation.AnnotationAttributes;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.core.type.AnnotationMetadata;
import org.tinyspring.core.type.classreading.MetadataReader;
import org.tinyspring.core.type.classreading.SimpleMetadataReader;
import org.tinyspring.stereotype.Component;

import java.io.IOException;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
public class MetadataReaderTest {

    @Test
    public void testGetMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinyspring/service/v4/PetStoreService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);

        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));

        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore",attributes.get("value"));

        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("org.tinyspring.service.v4.PetStoreService",amd.getClassName());

    }
}
