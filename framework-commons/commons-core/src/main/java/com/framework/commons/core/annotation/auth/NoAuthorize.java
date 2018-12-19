package com.framework.commons.core.annotation.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 * 不需要权限要求注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoAuthorize {
}
