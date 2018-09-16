package org.tinyspring.aop.aspectj;

import org.tinyspring.aop.Advice;
import org.tinyspring.aop.MethodMatcher;
import org.tinyspring.aop.Pointcut;
import org.tinyspring.aop.framework.AopConfigSupport;
import org.tinyspring.aop.framework.AopProxyFactory;
import org.tinyspring.aop.framework.CglibProxyFactory;
import org.tinyspring.beans.factory.config.BeanPostProcessor;
import org.tinyspring.beans.factory.config.ConfigurableBeanFactory;
import org.tinyspring.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/9/16
 */
public class AspectJAutoProxyCreator implements BeanPostProcessor {

    ConfigurableBeanFactory beanFactory;

    @Override
    public Object beforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) {

        if (isInfrastructureClass(bean.getClass())){
            return bean;
        }

        List<Advice> advices = getCandidateAdvices(bean);
        if (advices.isEmpty()){
            return bean;
        }

        return createProxy(advices,bean);
    }

    private Object createProxy(List<Advice> advices, Object bean) {

        AopConfigSupport config = new AopConfigSupport();
        for (Advice advice : advices){
            config.addAdvice(advice);
        }

        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());

        for (Class<?> targetInterface : targetInterfaces){
            config.addInterface(targetInterface);
        }

        config.setTargetObject(bean);

        AopProxyFactory proxyFactory = null;

        if (config.getProxiedInterfaces().length == 0){
            proxyFactory = new CglibProxyFactory(config);
        }

        return proxyFactory.getProxy();
    }

    private List<Advice> getCandidateAdvices(Object bean) {

        List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

        List<Advice> result = new ArrayList<>();

        for (Object o: advices){
            Pointcut pc = ((Advice)o).getPointcut();
            if (canApply(pc,bean.getClass())){
                result.add((Advice)o);
            }
        }

        return result;
    }

    private boolean canApply(Pointcut pc, Class<?> targetClass) {

        MethodMatcher methodMatcher = pc.getMethodMatcher();

        Set<Class> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
        classes.add(targetClass);
        for (Class<?> clazz : classes){
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods){
                if (methodMatcher.matches(method)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {

        boolean retVal = Advice.class.isAssignableFrom(beanClass);
        return retVal;
    }

    public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;

    }
}
