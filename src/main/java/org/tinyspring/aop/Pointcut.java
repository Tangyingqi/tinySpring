package org.tinyspring.aop;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public interface Pointcut {

    MethodMatcher getMethodMatcher();
    String getExpression();
}
