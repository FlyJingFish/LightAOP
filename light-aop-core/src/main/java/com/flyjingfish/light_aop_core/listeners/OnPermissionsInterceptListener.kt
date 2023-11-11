package com.flyjingfish.light_aop_core.listeners

import com.flyjingfish.light_aop_core.annotations.Permission
import org.aspectj.lang.ProceedingJoinPoint

interface OnPermissionsInterceptListener {
    fun requestPermission(
        joinPoint: ProceedingJoinPoint,
        permission : Permission,
        call: OnRequestPermissionListener
    )
}