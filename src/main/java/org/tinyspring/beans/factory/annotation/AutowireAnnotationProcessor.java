package org.tinyspring.beans.factory.annotation;

import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.tinyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.tinyspring.core.annotation.AnnotationUtils;
import org.tinyspring.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/28
 */
public class AutowireAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<>();

    public AutowireAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz){

        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do{
            LinkedList<InjectionElement> currElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()){
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null){
                    if (Modifier.isStatic(field.getModifiers())){
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field,required,beanFactory));
                }
            }

            elements.addAll(0,currElements);
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz,elements);
    }

    private boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(),this.requiredParameterName);
            if (method == null){
                return true;
            }

            return this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method,ann);
        }catch (Exception ex){
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes){
            Annotation ann = AnnotationUtils.getAnnotation(ao,type);
            if (ann != null){
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) {
        return false;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        }catch (Throwable ex){
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) {
        return null;
    }
}
