package com.flyjingfish.light_aop_core.utils

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.CodeSignature

object Utils {
    /**
     * 获取简约的方法名
     *
     * @param joinPoint
     * @return
     */
    fun getMethodName(joinPoint: ProceedingJoinPoint): String {
        val codeSignature = joinPoint.signature as CodeSignature
        val cls = codeSignature.declaringType //方法所在类
        val methodName = codeSignature.name //方法名
        return getClassName(cls) + "." + methodName
    }

    fun getClassName(cls: Class<*>?): String {
        if (cls == null) {
            return "<UnKnow Class>"
        }
        return if (cls.isAnonymousClass) {
            getClassName(cls.enclosingClass)
        } else cls.simpleName
    }
}