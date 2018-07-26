package org.tinyspring.beans.factory.annotation;

import org.tinyspring.beans.factory.BeanCreationException;
import org.tinyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.tinyspring.beans.factory.config.DependencyDescriptor;
import org.tinyspring.utils.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author tangyingqi
 * @date 2018/7/26
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required;

    public AutowiredFieldElement(Field f,boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField(){
        return (Field)this.member;
    }

    @Override
    public void inject(Object target) {

        Field field = this.getField();
        try {

            DependencyDescriptor desc = new DependencyDescriptor(field,required);
            Object value = factory.resolveDependency(desc);

            if (value != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target,value);
            }
        }catch (Throwable ex){
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }

    }
}
