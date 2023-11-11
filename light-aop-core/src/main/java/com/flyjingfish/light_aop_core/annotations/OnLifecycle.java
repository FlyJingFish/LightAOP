package com.flyjingfish.light_aop_core.annotations;

import androidx.lifecycle.Lifecycle;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.light_aop_core.cut.OnLifecycleCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 监听生命周期的操作，加入此注解可使你的方法内的代码在对应生命周期内才去执行
 */
@LightAopPointCut(OnLifecycleCut.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnLifecycle {
    Lifecycle.Event value();
}
