package org.tinyspring.beans.factory.support;

import org.tinyspring.beans.BeanDefinition;

/**
 * Created by tangyingqi on 2018/6/26.
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isProtoType() {
        return this.prototype;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.endsWith(scope);
    }

    public String getScope() {
        return scope;
    }
}
