package com.flyjingfish.lightaop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.flyjingfish.light_aop_core.annotations.CustomIntercept
import com.flyjingfish.light_aop_core.annotations.Permission
import com.flyjingfish.light_aop_core.listeners.OnCustomInterceptListener
import com.flyjingfish.light_aop_core.listeners.OnPermissionsInterceptListener
import com.flyjingfish.light_aop_core.listeners.OnRequestPermissionListener
import com.flyjingfish.light_aop_core.utils.LightAop
import com.flyjingfish.light_aop_core.utils.LightAop.setOnCustomInterceptListener
import com.flyjingfish.test_lib.BaseActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import org.aspectj.lang.ProceedingJoinPoint

class ThirdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LightAop.setOnPermissionsInterceptListener( object : OnPermissionsInterceptListener{
            override fun requestPermission(
                joinPoint: ProceedingJoinPoint,
                permission: Permission,
                call: OnRequestPermissionListener
            ) {
                val target = joinPoint.target
                if (target is FragmentActivity) {
                    val rxPermissions = RxPermissions(target)
                    rxPermissions.request(*permission.value).subscribe { isResult: Boolean ->
                        call.onCall(
                            isResult
                        )
                    }
                } else if (target is Fragment) {
                    val rxPermissions = RxPermissions(target)
                    rxPermissions.request(*permission.value).subscribe { isResult: Boolean ->
                        call.onCall(
                            isResult
                        )
                    }
                }
            }
        })

        LightAop.setOnCustomInterceptListener(object : OnCustomInterceptListener {
            override operator fun invoke(
                joinPoint: ProceedingJoinPoint,
                customIntercept: CustomIntercept
            ): Any? {
                // TODO: 2023/11/11 在此写你的逻辑 在合适的地方调用 joinPoint.proceed()，
                //  joinPoint.proceed(args)可以修改方法传入的参数
                return null
            }
        })
    }
}