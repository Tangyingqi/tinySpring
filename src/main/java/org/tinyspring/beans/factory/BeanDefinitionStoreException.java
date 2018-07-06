package org.tinyspring.beans.factory;

import org.tinyspring.beans.BeansException;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class BeanDefinitionStoreException extends BeansException {


    public BeanDefinitionStoreException(String msg) {
        this(msg,null);
    }

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
