package com.flyjingfish.light_aop_core.cut

import android.os.Looper
import android.util.Log
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_core.annotations.MainThread
import com.flyjingfish.light_aop_core.utils.AppExecutors
import org.aspectj.lang.ProceedingJoinPoint

class MainThreadCut : BasePointCut<MainThread>{
    override fun invoke(joinPoint: ProceedingJoinPoint, anno: MainThread): Any? {
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

}