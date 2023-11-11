package com.flyjingfish.lightaop.match;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flyjingfish.light_aop_annotation.LightAopMatchClassMethod;
import com.flyjingfish.light_aop_annotation.MatchClassMethod;
import com.flyjingfish.lightaop.BaseActivity;

import org.aspectj.lang.ProceedingJoinPoint;

@LightAopMatchClassMethod(targetClass = BaseActivity.class, methodName = "onCreate")
public class MatchActivityOnCreate implements MatchClassMethod {
    @Nullable
    @Override
    public Object invoke(@NonNull ProceedingJoinPoint joinPoint) {
        Log.e("MatchActivityOnCreate","invoke");
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            return null;
        }
    }
}
