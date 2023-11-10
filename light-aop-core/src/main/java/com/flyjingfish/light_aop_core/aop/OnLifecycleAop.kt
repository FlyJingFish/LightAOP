package com.flyjingfish.light_aop_core.aop

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.OnLifecycle
import org.aspectj.lang.ProceedingJoinPoint

class OnLifecycleAop :BaseLightAop<OnLifecycle> {
    override fun beforeInvoke(annotation: OnLifecycle) {
        Log.e("OnLifecycleAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: OnLifecycle): Any? {
        Log.e("OnLifecycleAop","invoke")
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
        return null
    }

    override fun afterInvoke(annotation: OnLifecycle) {
        Log.e("OnLifecycleAop","afterInvoke")
    }
}