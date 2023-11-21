package com.flyjingfish.light_aop_core.cut

import android.text.TextUtils
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_annotation.ProceedJoinPoint
import com.flyjingfish.light_aop_core.annotations.TryCatch
import com.flyjingfish.light_aop_core.utils.LightAop
import com.flyjingfish.light_aop_core.utils.Utils

class TryCatchCut :BasePointCut<TryCatch> {
    override fun invoke(joinPoint: ProceedJoinPoint, tryCatch: TryCatch): Any? {
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
}