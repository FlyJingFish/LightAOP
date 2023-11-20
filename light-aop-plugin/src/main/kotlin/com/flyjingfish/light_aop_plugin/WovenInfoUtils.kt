package com.flyjingfish.light_aop_plugin

import java.util.HashMap

object WovenInfoUtils {
    private val annotations : ArrayList<String> = ArrayList()
    private val classMethodRecords : HashMap<String,ArrayList<MethodRecord>> = HashMap()
    fun addAnnoInfo(info:String){
        annotations.add(info);
    }

    fun isContainAnno(info: String) :Boolean{
        val anno = "@" + info.substring(1,info.length).replace("/",".").replace(";","")
        return annotations.contains(anno);
    }

    fun addClassMethodRecords(classMethodRecord: ClassMethodRecord){
        var methodsRecord : ArrayList<MethodRecord>? = classMethodRecords[classMethodRecord.classFile]
        if (methodsRecord == null){
            methodsRecord =  ArrayList()
        }
        methodsRecord.add(classMethodRecord.methodName)
    }
}