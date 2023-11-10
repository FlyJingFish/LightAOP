package com.flyjingfish.light_aop_core.aop

import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.CustomIntercept
import org.aspectj.lang.ProceedingJoinPoint

class CustomInterceptAop : BaseLightAop<CustomIntercept> {
    override fun beforeInvoke(customIntercept: CustomIntercept) {
        Log.e("DefaultLightAop", "=====beforeInvoke====")
    }

    override operator fun invoke(
        joinPoint: ProceedingJoinPoint,
        customIntercept: CustomIntercept
    ): Any? {
        Log.e("DefaultLightAop", "=====invoke====")
        var oj: Any? = null
        oj = try {
            joinPoint!!.proceed()
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
        return oj
    }

    override fun afterInvoke(customIntercept: CustomIntercept) {
        Log.e("DefaultLightAop", "=====afterInvoke====")
    }
}