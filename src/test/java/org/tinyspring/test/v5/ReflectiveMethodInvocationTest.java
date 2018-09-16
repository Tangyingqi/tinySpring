package org.tinyspring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.tinyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.tinyspring.aop.aspectj.AspectJBeforeAdvice;
import org.tinyspring.aop.config.AspectInstanceFactory;
import org.tinyspring.aop.framework.ReflectiveMethodInvocation;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.service.v5.PetStoreService;
import org.tinyspring.tx.TransactionManager;
import org.tinyspring.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/6
 */
public class ReflectiveMethodInvocationTest extends AbstractTest{

    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
    private PetStoreService petStoreService = null;

    private BeanFactory beanFactory;
    private AspectInstanceFactory aspectInstanceFactory;

    @Before
    public void setUp() throws NoSuchMethodException {

        petStoreService = new PetStoreService();
        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        MessageTracker.cleanMsg();
        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"),
                null,
                aspectInstanceFactory);

        afterAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"),
                null,
                aspectInstanceFactory);
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                TransactionManager.class.getMethod("rollback"),
                null,
                aspectInstanceFactory
        );
    }

    @Test
    public void testMethodInvocation() throws Throwable {

        Method targetMethod = PetStoreService.class.getMethod("placeOrder");

        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,
                targetMethod,
                new Object[0],
                interceptors);

        mi.proceed();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

    @Test
    public void testMethodInvocation2() throws Throwable{

        Method targetMethod = PetStoreService.class.getMethod("placeOrder");

        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
        interceptors.add(afterAdvice);
        interceptors.add(beforeAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,
                targetMethod,
                new Object[0],
                interceptors);

        mi.proceed();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

    }

    @Test
    public void testAfterThrowing() throws Throwable{


        Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");

        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
        interceptors.add(afterThrowingAdvice);
        interceptors.add(beforeAdvice);



        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService,targetMethod,new Object[0],interceptors);
        try{
            mi.proceed();

        }catch(Throwable t){
            List<String> msgs = MessageTracker.getMsgs();
            Assert.assertEquals(2, msgs.size());
            Assert.assertEquals("start tx", msgs.get(0));
            Assert.assertEquals("rollback tx", msgs.get(1));
            return;
        }


        Assert.fail("No Exception thrown");


    }
}
