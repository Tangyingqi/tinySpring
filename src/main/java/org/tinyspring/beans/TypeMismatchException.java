package org.tinyspring.beans;

/**
 * @author tangyingqi
 * @date 2018/7/5
 */
public class TypeMismatchException extends BeansException {

    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("failed to convert value:" + value + " to type " + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }

    public Object getValue() {
        return value;
    }
}
