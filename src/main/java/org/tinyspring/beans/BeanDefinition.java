package org.tinyspring.beans;

import java.util.List;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public interface BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";

    String getBeanClassName();

    boolean isSingleton();

    boolean isProtoType();

    void setScope(String scope);

    String getScope();

    List<PropertyValue> getPropertyValues();
}
