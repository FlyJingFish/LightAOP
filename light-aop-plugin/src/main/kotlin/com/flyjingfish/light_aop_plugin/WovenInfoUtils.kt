package com.flyjingfish.light_aop_plugin

import java.util.HashMap

object WovenInfoUtils {
    val annotations: HashMap<String,AopMethodCut> = HashMap()
    private val classMethodRecords: HashMap<String, HashMap<String, MethodRecord>> = HashMap()//类名为key，value为方法map集合

    fun addAnnoInfo(info: AopMethodCut) {
        annotations[info.anno] = info
    }

    fun isContainAnno(info: String): Boolean {
        val anno = "@" + info.substring(1, info.length).replace("/", ".").replace(";", "")
        return annotations.contains(anno)
    }
    fun getAnnoInfo(info: String): AopMethodCut? {
        return annotations[info]
    }



    fun addClassMethodRecords(classMethodRecord: ClassMethodRecord) {
        var methodsRecord: HashMap<String, MethodRecord>? =
            classMethodRecords[classMethodRecord.classFile]
        if (methodsRecord == null) {
            methodsRecord = HashMap<String, MethodRecord>()
            classMethodRecords[classMethodRecord.classFile] = methodsRecord
        }
        val key = classMethodRecord.methodName.methodName + classMethodRecord.methodName.descriptor;
        methodsRecord[key] = classMethodRecord.methodName
    }

    fun getClassMethodRecord(classFile:String):HashMap<String, MethodRecord>?{
        val methodsRecord: HashMap<String, MethodRecord>? =
            classMethodRecords[classFile]
        return methodsRecord
    }
}