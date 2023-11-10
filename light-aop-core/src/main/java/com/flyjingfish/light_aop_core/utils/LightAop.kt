package com.flyjingfish.light_aop_core.utils

import com.flyjingfish.light_aop_core.listeners.OnCustomInterceptListener
import com.flyjingfish.light_aop_core.listeners.OnThrowableListener

object LightAop {
    private var onThrowableListener: OnThrowableListener? = null
    private var onCustomInterceptListener: OnCustomInterceptListener? = null

    fun setOnThrowableListener(listener: OnThrowableListener) {
        onThrowableListener = listener
    }

    fun getOnThrowableListener(): OnThrowableListener? {
        return onThrowableListener
    }

    fun setOnCustomInterceptListener(listener: OnCustomInterceptListener) {
        onCustomInterceptListener = listener
    }

    fun getOnCustomInterceptListener(): OnCustomInterceptListener? {
        return onCustomInterceptListener
    }


}