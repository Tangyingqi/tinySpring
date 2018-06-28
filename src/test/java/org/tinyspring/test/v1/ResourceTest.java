package org.tinyspring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;
import org.tinyspring.core.io.support.FileSystemResource;

import java.io.InputStream;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception{

        Resource r = new ClassPathResource("petstore-v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws Exception{

        Resource r = new FileSystemResource("src/test/resources/petstore-v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }
}
