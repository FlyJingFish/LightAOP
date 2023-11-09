package com.flyjingfish.lightaop;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeResolver {
    public static Class<? extends Annotation> resolveGenericType(Class<? extends Annotation> targetClass) {
        Type superclass = targetClass.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                return (Class<? extends Annotation>) actualTypeArguments[0];
            }
        }
        return null;
    }
}