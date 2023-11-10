package com.flyjingfish.light_aop_core.aop

import android.util.Log
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.annotations.Permission
import org.aspectj.lang.ProceedingJoinPoint

class PermissionAop :BaseLightAop<Permission> {
    override fun beforeInvoke(annotation: Permission) {
        Log.e("PermissionAop","beforeInvoke")
    }

    override fun invoke(joinPoint: ProceedingJoinPoint?, annotation: Permission): Any? {
        Log.e("PermissionAop","invoke")
        return null
    }

    override fun afterInvoke(annotation: Permission) {
        Log.e("PermissionAop","afterInvoke")
    }
}