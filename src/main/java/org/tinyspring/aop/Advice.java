package org.tinyspring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author tangyingqi
 * @date 2018/8/6
 */
public interface Advice extends MethodInterceptor {

    Pointcut getPointcut();
}
