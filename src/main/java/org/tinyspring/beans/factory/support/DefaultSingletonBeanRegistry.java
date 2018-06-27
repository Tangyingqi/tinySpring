package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.factory.config.SingletonBeanRegistry;
import org.tinyspring.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registrySingleton(String beanName, Object singletonObject) {

        Assert.notNull(beanName,"beanName must not null");

        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null){
            throw new IllegalStateException("Could not register object ["+ singletonObject + "] under bean name'"+
                                                 beanName + "':there is already object["+oldObject+"]");
        }
        this.singletonObjects.put(beanName,singletonObject);

    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
