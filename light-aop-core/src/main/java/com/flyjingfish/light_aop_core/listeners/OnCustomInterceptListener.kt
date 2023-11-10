package com.flyjingfish.light_aop_core.listeners

import com.flyjingfish.light_aop_core.annotations.CustomIntercept
import org.aspectj.lang.ProceedingJoinPoint

interface OnCustomInterceptListener {
    fun beforeInvoke(customIntercept: CustomIntercept)

    fun invoke(
        joinPoint: ProceedingJoinPoint,
        customIntercept: CustomIntercept
    ): Any?

    fun afterInvoke(customIntercept: CustomIntercept)
}