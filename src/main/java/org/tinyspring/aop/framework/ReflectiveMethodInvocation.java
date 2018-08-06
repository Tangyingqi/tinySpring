package org.tinyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;

    protected final Method targetMethod;

    protected Object[] arguments;

    protected final List<MethodInterceptor> interceptors;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object target,
                                      Method method,
                                      Object[] arguments,
                                      List<MethodInterceptor> interceptors){
        this.targetObject = target;
        this.targetMethod = method;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    @Override
    public Method getMethod() {
        return this.targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size()-1){
            return invokeJoinpoint();
        }

        this.currentInterceptorIndex++;
        MethodInterceptor interceptor
                = interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    protected Object invokeJoinpoint() throws InvocationTargetException, IllegalAccessException {
        return this.targetMethod.invoke(targetObject,arguments);
    }

    @Override
    public Object getThis() {
        return this.targetObject;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }
}
