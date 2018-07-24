package org.tinyspring.beans;

import java.util.List;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public interface BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    String getBeanClassName();

    boolean isSingleton();

    boolean isProtoType();

    void setScope(String scope);

    String getScope();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    String getID();

    boolean hasConstructorArgumentValues();

    boolean hasBeanClass();

    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    Class<?> getBeanClass();
}

