package com.flyjingfish.light_aop_core.aop

import android.text.TextUtils
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.TryCatch
import com.flyjingfish.light_aop_core.utils.LightAop
import com.flyjingfish.light_aop_core.utils.Utils
import org.aspectj.lang.ProceedingJoinPoint

class TryCatchAop :BaseLightAop<TryCatch> {
    override fun beforeInvoke(annotation: TryCatch) {
    }

    override fun invoke(joinPoint: ProceedingJoinPoint, tryCatch: TryCatch): Any? {
        var result: Any?
        try {
            result = joinPoint.proceed()
        } catch (e: Throwable) {
            var flag: String = tryCatch.value
            if (TextUtils.isEmpty(flag)) {
                flag = Utils.getMethodName(joinPoint)
            }
            result =  LightAop.getOnThrowableListener()?.handleThrowable(flag, e)
        }
        return result
    }

    override fun afterInvoke(annotation: TryCatch) {
    }
}