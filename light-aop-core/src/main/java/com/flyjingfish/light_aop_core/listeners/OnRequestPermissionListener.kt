package com.flyjingfish.light_aop_core.listeners

interface OnRequestPermissionListener {
    fun onCall(isResult: Boolean)
}