package com.flyjingfish.light_aop_core.annotations

import com.flyjingfish.light_aop_annotation.LightAopPointCut
import com.flyjingfish.light_aop_core.aop.PermissionAop

@LightAopPointCut(PermissionAop::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class MainThread()
