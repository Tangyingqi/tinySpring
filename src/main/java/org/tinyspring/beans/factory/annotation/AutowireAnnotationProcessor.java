package org.tinyspring.beans.factory.annotation;

import com.sun.deploy.util.ReflectionUtil;
import org.tinyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.tinyspring.core.annotation.AnnotationUtils;
import org.tinyspring.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author tangyingqi
 * @date 2018/7/28
 */
public class AutowireAnnotationProcessor {

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


}
