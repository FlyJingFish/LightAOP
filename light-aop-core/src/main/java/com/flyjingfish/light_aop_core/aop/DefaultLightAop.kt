package com.flyjingfish.light_aop_core.aop

import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.DefaultAnnotation
import org.aspectj.lang.ProceedingJoinPoint

class DefaultLightAop : BaseLightAop<DefaultAnnotation?> {
    override fun beforeInvoke(defaultAnnotation: DefaultAnnotation?) {
        Log.e("DefaultLightAop", "=====beforeInvoke====")
    }

    override operator fun invoke(
        joinPoint: ProceedingJoinPoint?,
        defaultAnnotation: DefaultAnnotation?
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

    override fun afterInvoke(defaultAnnotation: DefaultAnnotation?) {
        Log.e("DefaultLightAop", "=====afterInvoke====")
    }
}