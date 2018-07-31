package org.tinyspring.aop.config;

import org.tinyspring.beans.BeanUtils;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @author tangyingqi
 * @date 2018/7/31
 */
public class MethodLocatingFactory {

    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory){

        if (!StringUtils.hasText(targetBeanName)){
            throw new IllegalStateException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(methodName)){
            throw new IllegalStateException("Property 'methodName' is required");
        }

        Class<?> beanClass = beanFactory.getType(targetBeanName);
        if (beanClass == null){
            throw new IllegalStateException("Can't determine type of bean with name'"+this.targetBeanName+"'");
        }

        this.method = BeanUtils.resolveSignature(this.methodName,beanClass);

        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }


    public Method getObject(){
        return this.method;
    }
}
