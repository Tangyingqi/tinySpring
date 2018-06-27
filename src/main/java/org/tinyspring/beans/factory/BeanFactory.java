package org.tinyspring.beans.factory;


/**
 * Created by tangyingqi on 2018/6/26.
 */
public interface BeanFactory {

    Object getBean(String beanID);
}
