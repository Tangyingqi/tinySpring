package org.tinyspring.beans.factory.config;

import org.tinyspring.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author tangyingqi
 * @date 2018/7/24
 */
public class DependencyDescriptor {

    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field,"Field must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType(){
        if (this.field != null){
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return required;
    }
}
