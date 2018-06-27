package org.tinyspring.utils;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
public abstract class Assert {

    public static void notNull(Object object,String message){
        if (object == null){
            throw new IllegalArgumentException(message);
        }
    }

}
