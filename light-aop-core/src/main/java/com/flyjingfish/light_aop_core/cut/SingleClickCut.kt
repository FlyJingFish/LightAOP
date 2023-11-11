package com.flyjingfish.light_aop_core.cut

import android.view.View
import com.flyjingfish.light_aop_core.annotations.SingleClick
import org.aspectj.lang.ProceedingJoinPoint

class SingleClickCut : ClickCut<SingleClick>() {
    override fun invoke(joinPoint: ProceedingJoinPoint, anno: SingleClick): Any? {
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) {
                view = arg
                break
            }
        }
        if (view != null) {
            if (!isDoubleClick(view, anno.value)) {
                joinPoint.proceed()
            }
        }else{
            if (!isDoubleClick(anno.value)) {
                joinPoint.proceed()
            }
        }
        return null
    }

}