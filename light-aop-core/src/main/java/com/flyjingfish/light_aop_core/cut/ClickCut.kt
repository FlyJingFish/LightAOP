package com.flyjingfish.light_aop_core.cut

import android.view.View
import com.flyjingfish.light_aop_annotation.BasePointCut

abstract class ClickCut<T : Annotation> :BasePointCut<T> {
    private var sLastClickTime: Long = 0

    private var sLastClickViewId = 0

    fun isDoubleClick(v: View, intervalMillis: Long): Boolean {
        val time = System.currentTimeMillis()
        val viewId = v.id
        val timeD = time - sLastClickTime
        return if (timeD in 1 until intervalMillis && viewId == sLastClickViewId) {
            true
        } else {
            sLastClickTime = time
            sLastClickViewId = viewId
            false
        }
    }

    fun isDoubleClick(intervalMillis: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - sLastClickTime
        return if (timeD in 1 until intervalMillis) {
            true
        } else {
            sLastClickTime = time
            false
        }
    }
}