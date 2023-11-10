package com.flyjingfish.light_aop_annotation

import org.aspectj.lang.ProceedingJoinPoint

interface BaseLightAop<T : Annotation?> {
    fun beforeInvoke(annotation: T)
    operator fun invoke(joinPoint: ProceedingJoinPoint, annotation: T): Any?
    fun afterInvoke(annotation: T)
}