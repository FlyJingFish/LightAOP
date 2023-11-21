package com.flyjingfish.light_aop_annotation


interface BasePointCut<T : Annotation> {
    fun invoke(joinPoint: ProceedJoinPoint, anno: T): Any?
}