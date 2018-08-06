package org.tinyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/6
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject){
        super(adviceMethod,pointcut,adviceObject);
    }


    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.invokeAdviceMethod();
        return mi.proceed();
    }
}
