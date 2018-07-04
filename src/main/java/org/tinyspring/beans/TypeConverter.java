package org.tinyspring.beans;

/**
 * @author tangyingqi
 * @date 2018/7/5
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
