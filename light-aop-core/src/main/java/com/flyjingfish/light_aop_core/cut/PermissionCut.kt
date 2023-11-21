package com.flyjingfish.light_aop_core.cut

import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_annotation.ProceedJoinPoint
import com.flyjingfish.light_aop_core.annotations.Permission
import com.flyjingfish.light_aop_core.listeners.OnRequestPermissionListener
import com.flyjingfish.light_aop_core.utils.LightAop

class PermissionCut :BasePointCut<Permission> {
    override fun invoke(joinPoint: ProceedJoinPoint, anno: Permission): Any? {
        if (LightAop.getOnPermissionsInterceptListener() == null){
            return joinPoint.proceed()
        }
        LightAop.getOnPermissionsInterceptListener()?.requestPermission(joinPoint,anno,object : OnRequestPermissionListener{
            override fun onCall(isResult: Boolean) {
                if (isResult){
                    joinPoint.proceed()
                }
            }

        })
        return null
    }

}