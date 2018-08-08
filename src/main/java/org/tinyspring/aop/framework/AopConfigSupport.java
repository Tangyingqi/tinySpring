package org.tinyspring.aop.framework;

import org.tinyspring.aop.Advice;
import org.tinyspring.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class AopConfigSupport implements AopConfig{

    private boolean proxyTargetClass = false;

    private Object targetObject = null;

    private List<Advice> advices = new ArrayList<>();

    private List<Class> interfaces = new ArrayList<>();


    @Override
    public Class<?> getTargetClass() {
        return targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return targetObject;
    }

    @Override
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    @Override
    public Class<?> getProxiedInterfaces() {
        return null;
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        return false;
    }

    @Override
    public List<Advice> getAdvices() {
        return advices;
    }

    @Override
    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    @Override
    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<>();
        for (Advice advice : this.getAdvices()){
            Pointcut pc = advice.getPointcut();
            if (pc.getMethodMatcher().matches(method)){
                result.add(advice);
            }
        }
        return result;
    }

    @Override
    public void setTargetObject(Object obj) {
        this.targetObject = obj;
    }
}
