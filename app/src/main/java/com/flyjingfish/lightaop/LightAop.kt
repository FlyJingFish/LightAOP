package com.flyjingfish.lightaop


import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * 切面类
 */
@Aspect
class LightAop {
    // 切点
    @Pointcut("execution(@com.flyjingfish.lightaop.DebugLog * *(..))")
    fun debugLog() {
    }

    @Before("debugLog()")
    fun beforeDebugLog(joinPoint: JoinPoint) {
        try {
            val className = joinPoint.getThis().javaClass.simpleName
            val methodName = joinPoint.signature.name
            val signature = joinPoint.signature as MethodSignature
            val method = signature.method
            val annotation = method.getAnnotation(DebugLog::class.java)
            if (annotation == null) {
                Log.e(TAG, "before: annotation == null")
            } else {
                Log.i(TAG, "Method invoked: $className.$methodName")
            }
        } catch (e: Throwable) {
            Log.e(TAG, "beforeDebugLog: failed on error: ", e)
        }
    }

    companion object {
        private val TAG = LightAop::class.java.simpleName
    }
}