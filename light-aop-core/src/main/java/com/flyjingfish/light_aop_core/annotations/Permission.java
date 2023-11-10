package com.flyjingfish.light_aop_core.annotations;

import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.flyjingfish.light_aop_core.cut.PermissionCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@LightAopPointCut(PermissionCut.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Permission {
    /**
     * @return 需要申请权限的集合
     */
    String[] value();
}