package org.tinyspring.aop;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public interface MethodMatcher {

    boolean matches(Method method);
}
