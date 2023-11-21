package com.flyjingfish.light_aop_plugin

class AopMethodCut(val anno:String,val cutClassName:String) {
    override fun toString(): String {
        return "AopMethodCut(anno='$anno', cutClassName='$cutClassName')"
    }
}