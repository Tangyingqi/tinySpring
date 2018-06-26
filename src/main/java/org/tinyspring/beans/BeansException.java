package org.tinyspring.beans;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg){
        super(msg);
    }

    public BeansException(String msg,Throwable cause){
        super(msg,cause);
    }
}
