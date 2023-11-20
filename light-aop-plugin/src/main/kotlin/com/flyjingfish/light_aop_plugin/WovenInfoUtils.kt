package com.flyjingfish.light_aop_plugin

object WovenInfoUtils {
    private val annotations : ArrayList<String> = ArrayList()
    private val classMethodRecords : ArrayList<ClassMethodRecord> = ArrayList()
    fun addAnnoInfo(info:String){
        annotations.add(info);
    }

    fun isContainAnno(info: String) :Boolean{
        val anno = "@" + info.substring(1,info.length).replace("/",".").replace(";","")
//        println("isContainAnno$anno")
        return annotations.contains(anno);
    }

    fun addClassMethodRecords(classMethodRecord: ClassMethodRecord){
        classMethodRecords.add(classMethodRecord)
    }
}