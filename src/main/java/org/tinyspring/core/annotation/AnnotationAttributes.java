package org.tinyspring.core.annotation;

import org.tinyspring.utils.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
public class AnnotationAttributes extends LinkedHashMap<String,Object> {

    public AnnotationAttributes(){

    }

    public AnnotationAttributes(int initialCapacity){
        super(initialCapacity);
    }

    public AnnotationAttributes(Map<String,Object> map){
        super(map);
    }

    public String getString(String attributeName){
        return doGet(attributeName);
    }

    public String[] getStringArray(String attributeName){
        return doGet(attributeName);
    }

    public boolean getBoolean(String attributeName){
        return doGet(attributeName);
    }

    @SuppressWarnings("unchecked")
    public <N extends Number> N getNumber(String attributeName){
        return (N)doGet(attributeName);
    }

    public <E extends Enum<?>> E getEnum(String attributeName){
        return doGet(attributeName);
    }

    public <T> Class<? extends T> getClass(String attributeName) {
        return doGet(attributeName);
    }

    public Class<?>[] getClassArray(String attributeName) {
        return doGet(attributeName);
    }


    private <T> T doGet(String attributeName) {

        Object value = this.get(attributeName);
        Assert.notNull(value,String.format("Attribute '%s' not found",attributeName));
        return (T)value;
    }
}
