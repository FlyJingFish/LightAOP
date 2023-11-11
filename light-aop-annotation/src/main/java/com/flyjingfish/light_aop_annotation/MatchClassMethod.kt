package com.flyjingfish.light_aop_annotation

import org.aspectj.lang.ProceedingJoinPoint

interface MatchClassMethod {
    fun invoke(joinPoint: ProceedingJoinPoint,methodName:String): Any?
}