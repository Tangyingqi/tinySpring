package org.tinyspring.aop.framework;

import org.tinyspring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public interface AopConfig {

    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();

    Class<?> getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvices();

    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method);

    void setTargetObject(Object obj);
}
