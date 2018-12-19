package com.framework.commons.core.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于Service实现类注解，主要目的：当使用独立服务部署或“云部署”时，设置使用@Service，还是@RestController
 * 当使用云部署时，修改@Service为@RestController
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
@Service
//@RestController
public @interface FeignService {
    String value() default "";
}
