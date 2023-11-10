package com.flyjingfish.light_aop_core.cut

import android.util.Log
import android.view.View
import com.flyjingfish.light_aop_core.annotations.DoubleClick
import org.aspectj.lang.ProceedingJoinPoint

class DoubleClickCut : ClickCut<DoubleClick>() {
    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: DoubleClick): Any? {
        Log.e("DoubleClickAop","invoke")
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) {
                view = arg
                break
            }
        }
        if (view != null) {
            if (isDoubleClick(view, annotation.value)) {
                joinPoint.proceed()
            }
        }else{
            if (isDoubleClick(annotation.value)) {
                joinPoint.proceed()
            }
        }
        return null
    }

}