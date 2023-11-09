package com.flyjingfish.lightaop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;

public class DefaultLightAop implements BaseLightAop{
    @Override
    public void beforeInvoke(DebugLog debugLog) {
        Log.e("DefaultLightAop","=====beforeInvoke====");
    }

    @Override
    public Object invoke(ProceedingJoinPoint joinPoint,DebugLog debugLog) {
        Log.e("DefaultLightAop","=====invoke====");
        Object oj = null;
        try {
            oj= joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return oj;
    }

    @Override
    public void afterInvoke(DebugLog debugLog) {
        Log.e("DefaultLightAop","=====afterInvoke====");
    }
}
