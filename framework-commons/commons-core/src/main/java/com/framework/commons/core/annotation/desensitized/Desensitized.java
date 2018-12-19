package com.framework.commons.core.annotation.desensitized;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 * 敏感信息注解标记
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Desensitized {
    SensitiveType type();
}
