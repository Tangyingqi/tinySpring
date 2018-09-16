package org.tinyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.tinyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/6
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory){
        super(adviceMethod,pointcut,aspectInstanceFactory);
    }


    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.invokeAdviceMethod();
        return mi.proceed();
    }
}
