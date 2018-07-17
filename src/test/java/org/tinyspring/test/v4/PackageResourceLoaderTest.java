package org.tinyspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.PackageResourceLoader;

import java.io.IOException;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
public class PackageResourceLoaderTest {

    @Test
    public void testGetResource() throws IOException{
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.tinySpring.dao.v4");
        Assert.assertEquals(2,resources.length);
    }
}
