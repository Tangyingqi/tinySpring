package org.tinyspring.context.support;

import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.core.io.Resource;
import org.tinyspring.core.io.support.ClassPathResource;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private DefaultBeanFactory factory;

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);

    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());
    }


}
