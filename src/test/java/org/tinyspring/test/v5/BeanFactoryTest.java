package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.aop.Advice;
import org.tinyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.tinyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.tinyspring.aop.aspectj.AspectJBeforeAdvice;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.tx.TransactionManager;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/9/16
 */
public class BeanFactoryTest extends AbstractTest {

    static String expectedExpression = "execution(* org.tinyspring.service.v5.*.placeOrder(..))";

    @Test
    public void testGetBeanByType() throws NoSuchMethodException {

        BeanFactory beanFactory = super.getBeanFactory("petstore-v5.xml");

        List<Object> advices = beanFactory.getBeansByType(Advice.class);

        Assert.assertEquals(3,advices.size());

        {
            AspectJBeforeAdvice advice = (AspectJBeforeAdvice) this.getAdvice(AspectJBeforeAdvice.class,advices);

            Assert.assertEquals(TransactionManager.class.getMethod("start"),advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());
        }


        {
            AspectJAfterReturningAdvice advice = (AspectJAfterReturningAdvice)this.getAdvice(AspectJAfterReturningAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("commit"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }

        {
            AspectJAfterThrowingAdvice advice = (AspectJAfterThrowingAdvice)this.getAdvice(AspectJAfterThrowingAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("rollback"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }
    }


    public Object getAdvice(Class<?> type,List<Object> advices){
        for(Object o : advices){
            if(o.getClass().equals(type)){
                return o;
            }
        }
        return null;
    }

}
