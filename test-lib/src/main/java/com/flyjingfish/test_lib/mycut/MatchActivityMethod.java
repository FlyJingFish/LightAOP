package com.flyjingfish.test_lib.mycut;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flyjingfish.light_aop_annotation.LightAopMatchClassMethod;
import com.flyjingfish.light_aop_annotation.MatchClassMethod;
import com.flyjingfish.light_aop_annotation.ProceedJoinPoint;

import java.lang.reflect.InvocationTargetException;

@LightAopMatchClassMethod(targetClassName = "com.flyjingfish.test_lib.BaseActivity",methodName = {"onCreate","onResume"})
public class MatchActivityMethod implements MatchClassMethod {
    @Nullable
    @Override
    public Object invoke(@NonNull ProceedJoinPoint joinPoint, @NonNull String methodName) {
        Log.e("MatchActivityMethod","=====invoke====="+methodName);
        try {
            return joinPoint.proceed();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
