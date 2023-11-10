package com.flyjingfish.light_aop_core.annotations;

import androidx.lifecycle.Lifecycle;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.light_aop_core.cut.OnLifecycleCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@LightAopPointCut(OnLifecycleCut.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnLifecycle {
    Lifecycle.Event value();
}
