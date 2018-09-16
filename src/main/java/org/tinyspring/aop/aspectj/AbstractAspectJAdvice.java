package org.tinyspring.aop.aspectj;

import org.tinyspring.aop.Advice;
import org.tinyspring.aop.Pointcut;
import org.tinyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public abstract class AbstractAspectJAdvice implements Advice {

    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected AspectInstanceFactory aspectInstanceFactory;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 AspectInstanceFactory aspectInstanceFactory){

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    public void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        adviceMethod.invoke(aspectInstanceFactory.getAspectInstance());
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }
}
