package org.tinyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tinyspring.aop.Advice;
import org.tinyspring.utils.Assert;
import org.tinyspring.utils.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/9/30
 */
public class JdkAopProxyFactory implements AopProxyFactory,InvocationHandler {


    private AopConfig aopConfig;

    private static final Log logger = LogFactory.getLog(JdkAopProxyFactory.class);

    public JdkAopProxyFactory(AopConfig aopConfig) {
        Assert.notNull(aopConfig, "AdvisedSupport must not be null");
        if (aopConfig.getAdvices().size() == 0 ) {
            throw new AopConfigException("No advices specified");
        }
        this.aopConfig = aopConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object target = aopConfig.getTargetObject();

        Object revVal;

        List<Advice> chain = aopConfig.getAdvices(method);

        if (chain.isEmpty()){
            revVal = method.invoke(target,args);
        }else{
            List<MethodInterceptor> interceptors = new ArrayList<>();

            interceptors.addAll(chain);

            revVal = new ReflectiveMethodInvocation(target,method,args,interceptors).proceed();
        }
        return revVal;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        if (logger.isDebugEnabled()) {
            logger.debug("Creating JDK dynamic proxy: target source is " + this.aopConfig.getTargetObject());
        }
        Class<?>[] proxiedInterfaces = aopConfig.getProxiedInterfaces();
        return Proxy.newProxyInstance(classLoader,proxiedInterfaces,this);
    }
}
