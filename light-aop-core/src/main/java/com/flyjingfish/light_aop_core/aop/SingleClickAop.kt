package com.flyjingfish.light_aop_core.aop

import android.util.Log
import android.view.View
import com.flyjingfish.light_aop_core.annotations.SingleClick
import org.aspectj.lang.ProceedingJoinPoint

class SingleClickAop : ClickAop<SingleClick>() {
    override fun beforeInvoke(annotation: SingleClick) {
        Log.e("SingleClickAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: SingleClick): Any? {
        Log.e("SingleClickAop","invoke")
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) {
                view = arg
                break
            }
        }
        if (view != null) {
            if (!isDoubleClick(view, annotation.value)) {
                joinPoint.proceed()
            }
        }else{
            if (!isDoubleClick(annotation.value)) {
                joinPoint.proceed()
            }
        }
        return null
    }

    override fun afterInvoke(annotation: SingleClick) {
        Log.e("SingleClickAop","afterInvoke")
    }
}