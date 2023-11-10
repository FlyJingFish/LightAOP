package com.flyjingfish.light_aop_annotation

import org.aspectj.lang.ProceedingJoinPoint

interface BasePointCut<T : Annotation> {
    fun invoke(joinPoint: ProceedingJoinPoint, annotation: T): Any?
}