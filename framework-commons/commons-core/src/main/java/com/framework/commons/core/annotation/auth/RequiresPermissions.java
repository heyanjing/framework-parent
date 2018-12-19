package com.framework.commons.core.annotation.auth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 * 权限要求注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
    String[] value() default {};

    Logical logical() default Logical.AND;
}