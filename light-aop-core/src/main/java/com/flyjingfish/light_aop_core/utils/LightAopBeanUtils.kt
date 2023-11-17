package com.flyjingfish.light_aop_core.utils

import android.text.TextUtils
import com.flyjingfish.light_aop_annotation.BasePointCut
import com.flyjingfish.light_aop_annotation.MatchClassMethod
import com.flyjingfish.light_aop_core.cut.CustomInterceptCut
import org.aspectj.lang.ProceedingJoinPoint
import java.util.concurrent.ConcurrentHashMap

object LightAopBeanUtils {
    var basePointCutMap = ConcurrentHashMap<String, BasePointCut<*>?>()
    var matchClassMethodMap = ConcurrentHashMap<String, MatchClassMethod?>()

    fun getBasePointCut(joinPoint: ProceedingJoinPoint, clsName: String): BasePointCut<out Annotation> {
        val className = joinPoint.getThis().javaClass.name
        val methodName = joinPoint.signature.name
        val key = "$className.$methodName"
        var basePointCut: BasePointCut<*>?
        if (TextUtils.isEmpty(key)) {
            basePointCut = getNewPointCut(clsName)
        } else {
            basePointCut = basePointCutMap[key]
            if (basePointCut == null) {
                basePointCut = getNewPointCut(clsName)
                basePointCutMap[key] = basePointCut
            }
        }
        return basePointCut
    }

    private fun getNewPointCut(clsName: String): BasePointCut<out Annotation> {
        val cls: Class<out BasePointCut<out Annotation>> = try {
            Class.forName(clsName) as Class<out BasePointCut<out Annotation>>
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
        val basePointCut: BasePointCut<*> = if (cls == BasePointCut::class.java) {
            CustomInterceptCut()
        } else {
            try {
                cls.newInstance()
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            }
        }
        return basePointCut
    }


    fun getMatchClassMethod(joinPoint: ProceedingJoinPoint, clsName: String): MatchClassMethod {
        val className = joinPoint.getThis().javaClass.name
        val methodName = joinPoint.signature.name
        val key = "$className.$methodName"
        var matchClassMethod: MatchClassMethod?
        if (TextUtils.isEmpty(key)) {
            matchClassMethod = getNewMatchClassMethod(clsName)
        } else {
            matchClassMethod = matchClassMethodMap[key]
            if (matchClassMethod == null) {
                matchClassMethod = getNewMatchClassMethod(clsName)
                matchClassMethodMap[key] = matchClassMethod
            }
        }
        return matchClassMethod
    }

    private fun getNewMatchClassMethod(clsName: String): MatchClassMethod {
        val cls: Class<out MatchClassMethod> = try {
            Class.forName(clsName) as Class<out MatchClassMethod>
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
        return cls.newInstance()
    }
}