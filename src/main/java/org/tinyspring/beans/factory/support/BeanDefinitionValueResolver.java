package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.BeansException;
import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.BeanFactory;
import org.tinyspring.beans.factory.FactoryBean;
import org.tinyspring.beans.factory.config.RuntimeBeanReference;
import org.tinyspring.beans.factory.config.TypedStringValue;

/**
 * @author tangyingqi
 * @date 2018/7/4
 */
public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory factory;

    public BeanDefinitionValueResolver(AbstractBeanFactory factory) {
        this.factory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            return factory.getBean(refName);
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else if (value instanceof BeanDefinition){

            BeanDefinition bd = (BeanDefinition) value;

            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));

            return resolveInnerBean(innerBeanName,bd);
        }else{
            return value;
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {

        try {
            Object innerBean = this.factory.createBean(innerBd);

            if (innerBean instanceof FactoryBean){
                try {
                    return ((FactoryBean)innerBean).getObject();
                }catch (Exception e){
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            }else{
                return innerBean;
            }
        }catch (BeansException ex){
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClassName() + "] " : "")
                    , ex);
        }

    }
}
