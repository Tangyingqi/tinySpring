package org.tinyspring.test.v5;

import org.junit.Test;
import org.springframework.cglib.proxy.*;
import org.tinyspring.service.v5.PetStoreService;
import org.tinyspring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class CGLibTest {

    @Test
    public void testCallback(){

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(PetStoreService.class);

        enhancer.setCallback(new TransactionInterceptor());

        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();;
    }

    public static class TransactionInterceptor implements MethodInterceptor{
        TransactionManager txManager = new TransactionManager();
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            txManager.start();
            Object result = methodProxy.invokeSuper(obj,args);
            txManager.commit();
            return result;
        }
    }

    @Test
    public void testFilter(){

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);

        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new TransactionInterceptor(),NoOp.INSTANCE};

        Class<?>[] types = new Class[callbacks.length];
        for (int i = 0;i<types.length;i++){
            types[i] = callbacks[i].getClass();
        }

        enhancer.setCallbackTypes(types);
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);

        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        System.out.println(petStoreService.toString());
    }

    private static class ProxyCallbackFilter implements CallbackFilter{

        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")) {
                return 0;
            }else{
                return 1;
            }
        }
    }
}
