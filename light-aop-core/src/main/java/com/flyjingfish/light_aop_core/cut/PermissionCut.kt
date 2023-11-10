package com.flyjingfish.light_aop_core.cut

import android.util.Log
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_core.annotations.Permission
import org.aspectj.lang.ProceedingJoinPoint

class PermissionCut :BasePointCut<Permission> {
    override fun invoke(joinPoint: ProceedingJoinPoint, annotation: Permission): Any? {
        Log.e("PermissionAop","invoke")
        return null
    }

}