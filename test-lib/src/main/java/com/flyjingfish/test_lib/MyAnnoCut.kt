package com.flyjingfish.test_lib

import android.util.Log
import com.flyjingfish.light_aop_annotation.BasePointCut
import org.aspectj.lang.ProceedingJoinPoint

class MyAnnoCut : BasePointCut<MyAnno> {
    override fun invoke(
        joinPoint: ProceedingJoinPoint,
        annotation: MyAnno
    ): Any? {
        Log.e("MyAnnoCut","===invoke===")
        joinPoint.proceed()
        return null
    }
}