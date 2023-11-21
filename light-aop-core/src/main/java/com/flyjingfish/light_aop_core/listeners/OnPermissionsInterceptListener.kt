package com.flyjingfish.light_aop_core.listeners

import com.flyjingfish.light_aop_annotation.ProceedJoinPoint
import com.flyjingfish.light_aop_core.annotations.Permission

interface OnPermissionsInterceptListener {
    fun requestPermission(
        joinPoint: ProceedJoinPoint,
        permission : Permission,
        call: OnRequestPermissionListener
    )
}