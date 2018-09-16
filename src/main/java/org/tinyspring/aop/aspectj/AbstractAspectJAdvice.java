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
    protected AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 AspectInstanceFactory adviceObjectFactory){

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }

    public void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance(){
        return adviceObjectFactory.getAspectInstance();
    }
}
