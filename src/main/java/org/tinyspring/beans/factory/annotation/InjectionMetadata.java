package org.tinyspring.beans.factory.annotation;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2018/7/26
 */
public class InjectionMetadata {

    private final Class<?> targetClass;
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targeClass,List<InjectionElement> injectionElements){
        this.targetClass = targeClass;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target){
        if (injectionElements == null || injectionElements.isEmpty()){
            return;
        }
        for(InjectionElement ele : injectionElements){
            ele.inject(target);
        }
    }
}
