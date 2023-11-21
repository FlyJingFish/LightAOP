package com.flyjingfish.light_aop_plugin

object Utils {
    const val MethodAnnoUtils = "com.flyjingfish.light_aop_core.utils.MethodAnnoUtils"
    const val _CLASS = ".class"
    fun dotToSlash(str: String): String {
        return str.replace(".", "/")
    }

    fun slashToDot(str: String): String {
        return str.replace("/", ".")
    }

}