package com.flyjingfish.light_aop_core.cut

import android.os.Looper
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_core.annotations.OnLifecycle
import com.flyjingfish.light_aop_core.utils.AppExecutors
import org.aspectj.lang.ProceedingJoinPoint

class OnLifecycleCut :BasePointCut<OnLifecycle> {
    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: OnLifecycle): Any? {
        Log.e("OnLifecycleAop","invoke")

        if (Looper.getMainLooper() == Looper.myLooper()){
            invokeLifecycle(joinPoint, annotation)
        }else{
            AppExecutors.mainThread().execute {
                invokeLifecycle(joinPoint, annotation)
            }
        }
        return null
    }

    private fun invokeLifecycle(joinPoint: ProceedingJoinPoint, annotation: OnLifecycle){
        val target = joinPoint.target
        if(target is FragmentActivity){
            target.lifecycle.addObserver(object : LifecycleEventObserver{
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == annotation.value){
                        source.lifecycle.removeObserver(this)
                        joinPoint.proceed()
                    }
                }
            })
        }
    }
}