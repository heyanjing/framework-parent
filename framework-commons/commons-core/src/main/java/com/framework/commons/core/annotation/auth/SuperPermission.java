package com.framework.commons.core.annotation.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 * 超级用户admin注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SuperPermission {
    String value() default "";
}
