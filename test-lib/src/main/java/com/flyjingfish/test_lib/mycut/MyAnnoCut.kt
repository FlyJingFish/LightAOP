package com.flyjingfish.test_lib.mycut

import android.util.Log
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.test_lib.annotations.MyAnno
import org.aspectj.lang.ProceedingJoinPoint

class MyAnnoCut : BasePointCut<MyAnno> {
    override fun invoke(
        joinPoint: ProceedingJoinPoint,
        anno: MyAnno
    ): Any? {
        Log.e("MyAnnoCut","===invoke===")
        joinPoint.proceed()
        return null
    }
}