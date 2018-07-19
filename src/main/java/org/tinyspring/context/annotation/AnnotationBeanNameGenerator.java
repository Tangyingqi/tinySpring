package org.tinyspring.context.annotation;

import org.tinyspring.beans.BeanDefinition;
import org.tinyspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.tinyspring.beans.factory.support.BeanDefinitionRegistry;
import org.tinyspring.beans.factory.support.BeanNameGenerator;
import org.tinyspring.core.annotation.AnnotationAttributes;
import org.tinyspring.core.type.AnnotationMetadata;
import org.tinyspring.utils.ClassUtils;
import org.tinyspring.utils.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/19
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        if (beanDefinition instanceof AnnotatedBeanDefinition){
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition)beanDefinition);
            if (StringUtils.hasText(beanName)){
                return beanName;
            }
        }
        return buildDefaultBeanName(beanDefinition,registry);
    }

    private String buildDefaultBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(beanDefinition);
    }

    private String buildDefaultBeanName(BeanDefinition beanDefinition) {
        String shortClassName = ClassUtils.getShortName(beanDefinition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }

    private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata amd = beanDefinition.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for(String type : types){
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (attributes.get("value") != null){
                Object value = attributes.get("value");
                if (value instanceof String){
                    String strVal = (String)value;
                    if (StringUtils.hasLength(strVal)){
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }
}
