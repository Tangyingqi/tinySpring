package org.tinyspring.aop.aspectj;

import org.tinyspring.aop.Advice;
import org.tinyspring.aop.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public abstract class AbstractAspectJAdvice implements Advice {

    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 Object adviceObject){

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }

    public void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        adviceMethod.invoke(adviceObject);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }
}
