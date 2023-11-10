package com.flyjingfish.light_aop_core.aop

import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.MainThread
import org.aspectj.lang.ProceedingJoinPoint

class MainThreadAop : BaseLightAop<MainThread>{
    override fun beforeInvoke(annotation: MainThread) {
        Log.e("MainThreadAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint?, annotation: MainThread): Any? {
        Log.e("MainThreadAop","invoke")
        return null
    }

    override fun afterInvoke(annotation: MainThread) {
        Log.e("MainThreadAop","PermissionAop")
    }
}