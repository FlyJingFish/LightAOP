package com.flyjingfish.light_aop_annotation

interface MatchClassMethod {
    fun invoke(joinPoint: ProceedJoinPoint,methodName:String): Any?
}