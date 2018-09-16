package org.tinyspring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.tinyspring.aop.aspectj.AspectJBeforeAdvice;
import org.tinyspring.aop.aspectj.AspectJExpressionPointcut;
import org.tinyspring.aop.config.AspectInstanceFactory;
import org.tinyspring.aop.config.MethodLocatingFactory;
import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.ConstructorArgument.ValueHolder;
import org.tinyspring.beans.PropertyValue;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.support.DefaultBeanFactory;
import org.tinyspring.tx.TransactionManager;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tangyingqi
 * @date 2018/9/11
 */
public class BeanDefinitionTest extends AbstractTest {

    @Test
    public void testAOPBean() {

        DefaultBeanFactory beanFactory = (DefaultBeanFactory) this.getBeanFactory("petstore-v5.xml");

        {
            BeanDefinition bd = beanFactory.getBeanDefinition("tx");
            assertTrue(bd.getBeanClassName().equals(TransactionManager.class.getName()));
        }

        {
            BeanDefinition bd = beanFactory.getBeanDefinition("placeOrder");
            assertTrue(bd.isSynthetic());
            assertTrue(bd.getBeanClass().equals(AspectJExpressionPointcut.class));

            PropertyValue pv = bd.getPropertyValues().get(0);
            assertEquals("expression", pv.getName());
            assertEquals("execution(* org.tinyspring.service.v5.*.placeOrder(..))", pv.getValue());
        }

        {
            String name = AspectJBeforeAdvice.class.getName() + "#0";
            BeanDefinition bd = beanFactory.getBeanDefinition(name);
            assertTrue(bd.getBeanClass().equals(AspectJBeforeAdvice.class));

            assertTrue(bd.isSynthetic());

            List<ValueHolder> args = bd.getConstructorArgument().getArgumentValues();
            assertEquals(3, args.size());

            {
                BeanDefinition innerBeanDef = (BeanDefinition) args.get(0).getValue();
                assertTrue(innerBeanDef.isSynthetic());
                assertTrue(innerBeanDef.getBeanClass().equals(MethodLocatingFactory.class));

                List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
                Assert.assertEquals("targetBeanName",pvs.get(0).getName());
                Assert.assertEquals("tx",pvs.get(0).getValue());
                Assert.assertEquals("methodName",pvs.get(1).getName());
                Assert.assertEquals("start",pvs.get(1).getValue());
            }

            {
                RuntimeBeanReference ref = (RuntimeBeanReference)args.get(1).getValue();
                Assert.assertEquals("placeOrder",ref.getBeanName());
            }

            {
                BeanDefinition innerBeanDef = (BeanDefinition)args.get(2).getValue();
                Assert.assertTrue(innerBeanDef.isSynthetic());
                Assert.assertTrue(innerBeanDef.getBeanClass().equals(AspectInstanceFactory.class));

                List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
                Assert.assertEquals("aspectBeanName",pvs.get(0).getName());
                Assert.assertEquals("tx",pvs.get(0).getValue());
            }
        }

    }
}
