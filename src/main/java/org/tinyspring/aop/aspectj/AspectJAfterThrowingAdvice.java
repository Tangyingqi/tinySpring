package org.tinyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.tinyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory) {
        super(adviceMethod, pointcut, aspectInstanceFactory);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable t){
            invokeAdviceMethod();
            throw t;
        }
    }
}
