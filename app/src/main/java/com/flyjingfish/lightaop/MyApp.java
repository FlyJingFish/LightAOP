package com.flyjingfish.lightaop;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.flyjingfish.light_aop_core.annotations.CustomIntercept;
import com.flyjingfish.light_aop_core.annotations.Permission;
import com.flyjingfish.light_aop_core.listeners.OnCustomInterceptListener;
import com.flyjingfish.light_aop_core.listeners.OnPermissionsInterceptListener;
import com.flyjingfish.light_aop_core.listeners.OnRequestPermissionListener;
import com.flyjingfish.light_aop_core.listeners.OnThrowableListener;
import com.flyjingfish.light_aop_core.utils.LightAop;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.aspectj.lang.ProceedingJoinPoint;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LightAop.INSTANCE.setOnPermissionsInterceptListener(new OnPermissionsInterceptListener() {
            @SuppressLint("CheckResult")
            @Override
            public void requestPermission(@NonNull ProceedingJoinPoint joinPoint, @NonNull Permission permission, @NonNull OnRequestPermissionListener call) {
                Object target =  joinPoint.getTarget();
                if (target instanceof FragmentActivity){
                    RxPermissions rxPermissions = new RxPermissions((FragmentActivity) target);
                    rxPermissions.request(permission.value()).subscribe(call::onCall);
                }else if (target instanceof Fragment){
                    RxPermissions rxPermissions = new RxPermissions((Fragment) target);
                    rxPermissions.request(permission.value()).subscribe(call::onCall);
                }
            }
        });

        LightAop.INSTANCE.setOnCustomInterceptListener(new OnCustomInterceptListener() {
            @Nullable
            @Override
            public Object invoke(@NonNull ProceedingJoinPoint joinPoint, @NonNull CustomIntercept customIntercept) {
                // TODO: 2023/11/11 在此写你的逻辑 在合适的地方调用 joinPoint.proceed()，
                //  joinPoint.proceed(args)可以修改方法传入的参数，如果需要改写返回值，则在 return 处返回即可

                return null;
            }
        });

        LightAop.INSTANCE.setOnThrowableListener(new OnThrowableListener() {
            @Nullable
            @Override
            public Object handleThrowable(@NonNull String flag, @Nullable Throwable throwable) {
                // TODO: 2023/11/11 发生异常可根据你当时传入的flag作出相应处理，如果需要改写返回值，则在 return 处返回即可
                return 3;
            }
        });
    }
}
