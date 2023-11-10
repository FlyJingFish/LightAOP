package com.flyjingfish.light_aop_core.aop

import android.os.Looper
import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.MainThread
import com.flyjingfish.light_aop_core.utils.AppExecutors
import org.aspectj.lang.ProceedingJoinPoint

class MainThreadAop : BaseLightAop<MainThread>{
    override fun beforeInvoke(annotation: MainThread) {
        Log.e("MainThreadAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: MainThread): Any? {
        Log.e("MainThreadAop","invoke")
        if (Looper.getMainLooper() == Looper.myLooper()){
            joinPoint.proceed()
        }else{
            AppExecutors.mainThread().execute {
                try {
                    joinPoint.proceed()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    override fun afterInvoke(annotation: MainThread) {
        Log.e("MainThreadAop","afterInvoke")
    }
}