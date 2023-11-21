package com.flyjingfish.test_lib.annotation;

import androidx.annotation.Keep;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.test_lib.mycut.MyAnnoCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@LightAopPointCut(MyAnnoCut.class)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Keep
public @interface MyAnno {
}
