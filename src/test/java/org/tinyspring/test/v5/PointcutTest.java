package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.aop.MethodMatcher;
import org.tinyspring.aop.aspectj.AspectJExpressionPointcut;
import org.tinyspring.service.v5.PetStoreService;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public class PointcutTest {

    @Test
    public void testPointcut() throws NoSuchMethodException {
        String expression = "execution(* org.tinyspring.service.v5.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();

        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStoreService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = org.tinyspring.service.v4.PetStoreService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }
    }
}
