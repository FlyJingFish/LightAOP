package com.flyjingfish.light_aop_core;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SampleAspect {
    @Pointcut("execution(void com.flyjingfish.lightaop.BaseActivity+.onCreate(..))")
    public void activityOnCreate() {

    }
    @Around("activityOnCreate()")
    public Object activityOnCreateTime(ProceedingJoinPoint joinPoint) {
//        String className = joinPoint.getThis().getClass().getName();
//        joinPoint.getSourceLocation().getWithinType().getName()//拿到具体的某个类
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Log.d("SampleAspect","activityOnCreateTime:"+className+"."+methodName+"==+"+joinPoint.getSourceLocation().getFileName()+"=="+joinPoint.getSourceLocation().getWithinType().getName()+"=="+joinPoint.getSourceLocation().getLine());
        Object object = null;
        long startTime = System.currentTimeMillis();
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return object;
    }
}