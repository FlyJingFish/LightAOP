package com.flyjingfish.light_aop_annotation;

import java.lang.reflect.Method;

public class LightMethod {
    private Method originalMethod;
    public String name;
    void setOriginalMethod(Method originalMethod) {
        this.originalMethod = originalMethod;
        this.name = originalMethod.getName();
    }
}
