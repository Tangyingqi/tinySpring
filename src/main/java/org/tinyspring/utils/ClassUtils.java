package org.tinyspring.utils;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public abstract class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }catch (Throwable ex){

        }
        if (cl == null){
            cl = ClassUtils.class.getClassLoader();
            if (cl == null){
                try{
                    cl = ClassLoader.getSystemClassLoader();
                }catch (Throwable ex){

                }
            }
        }
        return cl;
    }
}
