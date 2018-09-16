package org.tinyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.tinyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory){
        super(adviceMethod,pointcut,aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object o = mi.proceed();
        this.invokeAdviceMethod();
        return o;
    }
}
