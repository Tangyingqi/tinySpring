package org.tinyspring.context.support;

import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.FileSystemResource;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {


    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }

}
