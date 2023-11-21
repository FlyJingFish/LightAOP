package com.flyjingfish.light_aop_core.cut

import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_annotation.ProceedJoinPoint
import com.flyjingfish.light_aop_core.annotations.CustomIntercept
import com.flyjingfish.light_aop_core.utils.LightAop

class CustomInterceptCut : BasePointCut<CustomIntercept> {
    override fun invoke(
        joinPoint: ProceedJoinPoint,
        anno: CustomIntercept
    ): Any? {
        if (LightAop.getOnCustomInterceptListener() == null){
            return joinPoint.proceed()
        }
        return LightAop.getOnCustomInterceptListener()?.invoke(joinPoint, anno)
    }
}