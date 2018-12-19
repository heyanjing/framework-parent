package com.framework.commons.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 * 字段别名（在对象复制时，别名与字段名相同的也执行复制）
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapped {

    String[] names() default {};
}
