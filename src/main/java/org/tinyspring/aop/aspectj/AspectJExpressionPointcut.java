package org.tinyspring.aop.aspectj;

import org.aspectj.weaver.reflect.ReflectionWorld;
import org.aspectj.weaver.tools.*;
import org.tinyspring.aop.MethodMatcher;
import org.tinyspring.aop.Pointcut;
import org.tinyspring.utils.ClassUtils;
import org.tinyspring.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public class AspectJExpressionPointcut implements Pointcut,MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    private String expression;

    private PointcutExpression pointcutExpression;

    private ClassLoader pointcutClassLoader;

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean matches(Method method) {

        checkReadyToMatch();

        ShadowMatch shadowMatch = getShadowMatch(method);

        return shadowMatch.alwaysMatches();
    }

    private ShadowMatch getShadowMatch(Method method){

        ShadowMatch shadowMatch = null;

        try {
            shadowMatch = pointcutExpression.matchesMethodExecution(method);
        }catch (ReflectionWorld.ReflectionWorldException ex){
            throw new RuntimeException("not implemented yet");
        }
        return shadowMatch;
    }

    private void checkReadyToMatch(){
        if (getExpression() == null){
            throw new IllegalStateException("Must set property 'expression' before attempting to match");
        }

        if (this.pointcutExpression == null){
            this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
            this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
        }
    }

    private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {

        PointcutParser parser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES,classLoader);
        return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()),null,new PointcutParameter[0]);
    }

    private String replaceBooleanOperators(String pcExpr){
        String result = StringUtils.replace(pcExpr," and "," && ");
        result = StringUtils.replace(result," or "," || ");
        result = StringUtils.replace(result," not "," ! ");
        return result;
    }
}
