package com.flyjingfish.light_aop_core.utils;

import com.flyjingfish.light_aop_annotation.JoinAnnoCutUtils;

public class MethodAnnoUtils {
    public static void registerMap(){
//        register("");
    }

    public static void register(String mapValue){
        JoinAnnoCutUtils.register(mapValue);
    }
}
