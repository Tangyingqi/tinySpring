package org.tinyspring.stereotype;

import java.lang.annotation.*;

/**
 * @author tangyingqi
 * @date 2018/7/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
