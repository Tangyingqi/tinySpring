package org.tinyspring.aop.framework;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public interface AopProxyFactory {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
