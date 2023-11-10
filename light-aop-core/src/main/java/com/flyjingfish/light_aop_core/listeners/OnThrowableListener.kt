package com.flyjingfish.light_aop_core.listeners

interface OnThrowableListener {
    fun handleThrowable(flag: String, throwable: Throwable?): Any?
}