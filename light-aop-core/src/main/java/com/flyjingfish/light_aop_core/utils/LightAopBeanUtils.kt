package com.flyjingfish.light_aop_core.utils

import android.text.TextUtils
import com.flyjingfish.light_aop_annotation.BaseLightAop
import com.flyjingfish.light_aop_core.aop.DefaultLightAop
import org.aspectj.lang.ProceedingJoinPoint
import java.util.concurrent.ConcurrentHashMap

object LightAopBeanUtils {
    var baseLightAopMap = ConcurrentHashMap<String, BaseLightAop<*>?>()

    fun getBaseLightAop(joinPoint: ProceedingJoinPoint, clsName: String): BaseLightAop<*>? {
        val className = joinPoint.getThis().javaClass.name
        val methodName = joinPoint.signature.name
        val key = "$className.$methodName"
        var baseLightAop: BaseLightAop<*>?
        if (TextUtils.isEmpty(key)) {
            baseLightAop = getNewAop(clsName)
        } else {
            baseLightAop = baseLightAopMap[key]
            if (baseLightAop == null) {
                baseLightAop = getNewAop(clsName)
                baseLightAopMap[key] = baseLightAop
            }
        }
        return baseLightAop
    }

    private fun getNewAop(clsName: String): BaseLightAop<*>? {
        val cls: Class<out BaseLightAop<*>> = try {
            Class.forName(clsName) as Class<out BaseLightAop<*>>
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
        val baseLightAop: BaseLightAop<*> = if (cls == BaseLightAop::class.java) {
            DefaultLightAop()
        } else {
            try {
                cls.newInstance()
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            }
        }
        return baseLightAop
    }
}