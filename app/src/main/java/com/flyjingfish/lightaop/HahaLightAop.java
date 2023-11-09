package com.flyjingfish.lightaop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class HahaLightAop implements BaseLightAop{
    @Override
    public void beforeInvoke(DebugLog debugLog) {
        Log.e("HahaLightAop","=====beforeInvoke====");
    }
    @Override
    public Object invoke(ProceedingJoinPoint joinPoint,DebugLog debugLog) {
        Log.e("HahaLightAop","=====invoke===="+debugLog.lannotation().stringValues()[0]);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
//        Annotation[] annotations = method.getAnnotations();
//        for (Annotation annotation : annotations) {
//            if (annotation.annotationType() == DebugLog2.class){
//                DebugLog2 ann = (DebugLog2) method.getAnnotation(annotation.annotationType());
//                Log.e("HahaLightAop","=====invoke===="+ann.value());
//            }
//        }
//        DebugLog2 annotation = method.getAnnotation(DebugLog2.class);
//        ConstructorSignature signature = (ConstructorSignature) joinPoint.getSignature();
//        Constructor method = signature.getConstructor();
////        DebugLog2 annotation = (DebugLog2) method.getAnnotation(DebugLog2.class);
//        Annotation[] annotations = method.getAnnotations();
//        for (Annotation annotation : annotations) {
//            if (annotation.annotationType() == DebugLog2.class){
//                DebugLog2 ann = (DebugLog2) method.getAnnotation(annotation.annotationType());
//                Log.e("HahaLightAop","=====invoke===="+ann.value());
//            }
//        }
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
        Log.e("HahaLightAop","=====afterInvoke====");
    }
}
