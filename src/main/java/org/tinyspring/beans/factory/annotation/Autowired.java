package org.tinyspring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;
}

