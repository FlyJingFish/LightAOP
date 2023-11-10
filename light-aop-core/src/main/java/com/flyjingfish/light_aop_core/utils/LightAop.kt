package com.flyjingfish.light_aop_core.utils

import com.flyjingfish.light_aop_core.listeners.OnThrowableListener

object LightAop {
    private var onThrowableListener: OnThrowableListener? = null

    fun setOnThrowableListener(listener: OnThrowableListener) {
        onThrowableListener = listener
    }

    fun getOnThrowableListener(): OnThrowableListener? {
        return onThrowableListener
    }
}