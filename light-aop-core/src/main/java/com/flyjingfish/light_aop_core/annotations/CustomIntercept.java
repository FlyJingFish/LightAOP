package com.flyjingfish.light_aop_core.annotations;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.light_aop_core.cut.CustomInterceptCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义拦截，此注解可以加到方法和构造器上
 */
@LightAopPointCut(CustomInterceptCut.class)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomIntercept {
    String[] value() default {};
}
