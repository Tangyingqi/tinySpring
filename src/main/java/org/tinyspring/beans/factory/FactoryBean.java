package org.tinyspring.beans.factory;

/**
 * @author tangyingqi
 * @date 2018/9/11
 */
public interface FactoryBean<T> {

    T getObject();

    Class<?> getObjectType();
}
