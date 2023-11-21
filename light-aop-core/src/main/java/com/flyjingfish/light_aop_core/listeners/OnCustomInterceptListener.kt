package com.flyjingfish.light_aop_core.listeners

import com.flyjingfish.light_aop_annotation.ProceedJoinPoint
import com.flyjingfish.light_aop_core.annotations.CustomIntercept

interface OnCustomInterceptListener {
    fun invoke(
        joinPoint: ProceedJoinPoint,
        customIntercept: CustomIntercept
    ): Any?

}