package com.flyjingfish.light_aop_annotation;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(SOURCE)
public @interface LightAopMatchClassMethod {
    String targetClassName();

    String[] methodName();
}
