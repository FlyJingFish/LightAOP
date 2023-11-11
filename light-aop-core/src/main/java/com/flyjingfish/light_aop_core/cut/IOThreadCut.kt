package com.flyjingfish.light_aop_core.cut

import android.os.Looper
import android.util.Log
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_core.annotations.IOThread
import com.flyjingfish.light_aop_core.enums.ThreadType
import com.flyjingfish.light_aop_core.utils.AppExecutors
import org.aspectj.lang.ProceedingJoinPoint

class IOThreadCut : BasePointCut<IOThread>{
    override fun invoke(joinPoint: ProceedingJoinPoint, ioThread: IOThread): Any? {
        if (Looper.getMainLooper() != Looper.myLooper()){
            return joinPoint.proceed()
        }else{
//            val result: Any? = when (ioThread.value) {
//                ThreadType.Single, ThreadType.Disk -> AppExecutors.singleIO().submit(
//                    Callable<Any?> { getProceedResult(joinPoint) }).get()
//
//                ThreadType.Fixed, ThreadType.Network -> AppExecutors.poolIO()
//                    .submit(Callable<Any?> { getProceedResult(joinPoint) }).get()
//            }
            when (ioThread.value) {
                ThreadType.SingleIO, ThreadType.DiskIO -> AppExecutors.singleIO().execute {
                    getProceedResult(
                        joinPoint
                    )
                }

                ThreadType.MultipleIO, ThreadType.NetworkIO -> AppExecutors.poolIO()
                    .execute { getProceedResult(joinPoint) }
            }
            return null
        }
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