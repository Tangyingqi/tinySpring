package org.tinyspring.beans.factory;


import java.util.List;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public interface BeanFactory {

    Object getBean(String beanID);

    Class<?> getType(String name);

    List<Object> getBeansByType(Class<?> type);
}
