package org.tinyspring.aop.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;
import org.tinyspring.aop.Advice;
import org.tinyspring.utils.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class CglibProxyFactory implements AopProxyFactory{

    // Constants for CGLIB callback array indices
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;

    protected static final Log logger = LogFactory.getLog(CglibProxyFactory.class);

    protected final AopConfig config;

    private Object[] constructorArgs;

    private Class<?> constructorArgTypes;

    public CglibProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advisors specified");
        }
        this.config = config;

    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }

        try{
            Class<?> rootClass  = this.config.getTargetObject().getClass();

            Enhancer enhancer = new Enhancer();
            if (classLoader != null){
                enhancer.setClassLoader(classLoader);
            }

            enhancer.setSuperclass(rootClass);

            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
            enhancer.setInterceptDuringConstruction(false);

            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class[callbacks.length];

            for (int i =0;i<types.length;i++){
                types[i] = callbacks[i].getClass();
            }

            enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
            enhancer.setCallbackTypes(types);
            enhancer.setCallbacks(callbacks);

            return enhancer.create();

        }catch (CodeGenerationException | IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (Exception ex) {
            throw new AopConfigException("Unexpected AOP exception", ex);
        }
    }

    private Callback[] getCallbacks(Class<?> rootClass){

        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);

        Callback[] callbacks = new Callback[]{aopInterceptor};

        return callbacks;
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor{

        private final AopConfig aopConfig;

        DynamicAdvisedInterceptor(AopConfig advised){
            this.aopConfig = advised;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            Object target = this.aopConfig.getTargetObject();

            List<Advice> chain = this.aopConfig.getAdvices(method);

            Object retVal;

            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())){
                retVal = methodProxy.invoke(target,args);
            }else{

                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<>(chain);
                retVal = new ReflectiveMethodInvocation(target,method,args,interceptors).proceed();
            }
            return retVal;
        }
    }

    private static class ProxyCallbackFilter implements CallbackFilter{

        private final AopConfig config;

        public ProxyCallbackFilter(AopConfig advised) {
            this.config = advised;
        }


        @Override
        public int accept(Method method) {
            return AOP_PROXY;
        }
    }
}
