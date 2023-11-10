package com.flyjingfish.light_aop_core.aop

import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.CustomIntercept
import com.flyjingfish.light_aop_core.utils.LightAop
import org.aspectj.lang.ProceedingJoinPoint

class CustomInterceptAop : BaseLightAop<CustomIntercept> {
    override fun beforeInvoke(annotation: CustomIntercept) {
        LightAop.getOnCustomInterceptListener()?.beforeInvoke(annotation)
    }

    override fun invoke(
        joinPoint: ProceedingJoinPoint,
        annotation: CustomIntercept
    ): Any? {
        return LightAop.getOnCustomInterceptListener()?.invoke(joinPoint, annotation)
    }

    override fun afterInvoke(annotation: CustomIntercept) {
        LightAop.getOnCustomInterceptListener()?.afterInvoke(annotation)
    }
}