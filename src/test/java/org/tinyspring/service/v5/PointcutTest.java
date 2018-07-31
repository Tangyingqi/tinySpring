package org.tinyspring.service.v5;

import org.junit.Test;

/**
 * @author tangyingqi
 * @date 2018/7/30
 */
public class PointcutTest {

    @Test
    public void testPointcut(){

        String expression = "execution(* org.tinyspring.service.v5.*.placeOrder(..))";

        //AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
    }
}
