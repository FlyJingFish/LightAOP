package com.flyjingfish.light_aop_core.annotations;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.light_aop_core.cut.CustomInterceptCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@LightAopPointCut(CustomInterceptCut.class)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomIntercept {
    String[] value() default {};
}
