package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.tinyspring.aop.aspectj.AspectJBeforeAdvice;
import org.tinyspring.aop.aspectj.AspectJExpressionPointcut;
import org.tinyspring.aop.config.AspectInstanceFactory;
import org.tinyspring.aop.framework.AopConfig;
import org.tinyspring.aop.framework.AopConfigSupport;
import org.tinyspring.aop.framework.CglibProxyFactory;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.service.v5.PetStoreService;
import org.tinyspring.tx.TransactionManager;
import org.tinyspring.util.MessageTracker;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/8/7
 */
public class CGLibAopProxyTest extends AbstractTest {

    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterReturningAdvice = null;
    private static AspectJExpressionPointcut pc = null;

    private BeanFactory beanFactory;
    private AspectInstanceFactory aspectInstanceFactory;

    @Before
    public void setUp() throws NoSuchMethodException {

        MessageTracker.cleanMsg();

        String expression = "execution(* org.tinyspring.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                TransactionManager.class.getMethod("start"),
                pc,
                aspectInstanceFactory);

        afterReturningAdvice = new AspectJAfterReturningAdvice(
                TransactionManager.class.getMethod("commit"),
                pc,
                aspectInstanceFactory
        );
    }

    @Test
    public void testGetProxy() {

        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterReturningAdvice);
        config.setTargetObject(new PetStoreService());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();

        proxy.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

    }
}
