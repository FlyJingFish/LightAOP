package com.flyjingfish.lightaop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
public class LightAop2 {
    private static final String TAG = LightAop2.class.getSimpleName();
    @Pointcut("execution(@com.flyjingfish.lightaop.DebugLog2 * *(..))")
    public void debugLog() {
    }

    @Before("debugLog()")
    public void beforeDebugLog(JoinPoint joinPoint) {
        try {
            String className = joinPoint.getThis().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation annotation = method.getAnnotation(DebugLog2.class);
            if (annotation == null) {
                Log.e(TAG, "before: annotation == null");
            } else {
                Log.i(TAG, "Method invoked: "+className+"."+methodName);
            }
        } catch (Throwable e) {
            Log.e(TAG, "beforeDebugLog: failed on error: ", e);
        }
    }

}
