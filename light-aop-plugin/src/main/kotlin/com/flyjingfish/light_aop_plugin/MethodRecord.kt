package com.flyjingfish.light_aop_plugin

class MethodRecord(val methodName :String,val descriptor:String) {
    override fun toString(): String {
        return "MethodRecord(methodName='$methodName', descriptor='$descriptor')"
    }
}