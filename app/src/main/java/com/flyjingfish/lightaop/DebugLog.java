package com.flyjingfish.lightaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 * @author xuexiang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DebugLog {

    /**
     * @return 日志的优先级(默认是0)
     */
    int priority() default 0;

    Class<? extends BaseLightAop> annotationClass() default DefaultLightAop.class;


    LightAopAnnotation lannotation() default @LightAopAnnotation;
}