package com.flyjingfish.light_aop_core.aop

import android.os.Looper
import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.IOThread
import com.flyjingfish.light_aop_core.enums.ThreadType
import com.flyjingfish.light_aop_core.utils.AppExecutors
import org.aspectj.lang.ProceedingJoinPoint
import java.util.concurrent.Callable

class IOThreadAop : BaseLightAop<IOThread>{
    override fun beforeInvoke(annotation: IOThread) {
        Log.e("IOThreadAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint, ioThread: IOThread): Any? {
        Log.e("IOThreadAop","invoke")
        if (Looper.getMainLooper() != Looper.myLooper()){
            joinPoint.proceed()
        }else{
            val result: Any? = when (ioThread.value) {
                ThreadType.Single, ThreadType.Disk -> AppExecutors.singleIO().submit(
                    Callable<Any?> { getProceedResult(joinPoint) }).get()

                ThreadType.Fixed, ThreadType.Network -> AppExecutors.poolIO()
                    .submit(Callable<Any?> { getProceedResult(joinPoint) }).get()
            }
            return result
        }
        return null
    }

    override fun afterInvoke(annotation: IOThread) {
        Log.e("IOThreadAop","afterInvoke")
    }

    private fun getProceedResult(joinPoint: ProceedingJoinPoint): Any? {
        try {
            return joinPoint.proceed()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null
    }
}