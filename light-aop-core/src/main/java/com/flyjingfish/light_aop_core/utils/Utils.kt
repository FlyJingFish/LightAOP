package com.flyjingfish.light_aop_core.utils

import com.flyjingfish.light_aop_annotation.ProceedJoinPoint

object Utils {
    /**
     * 获取简约的方法名
     *
     * @param joinPoint
     * @return
     */
    fun getMethodName(joinPoint: ProceedJoinPoint): String {
        val methodName = joinPoint.targetMethod.name //方法名
        return getClassName(joinPoint.target.javaClass) + "." + methodName
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