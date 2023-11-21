package com.flyjingfish.light_aop_annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProceedJoinPoint {
    public Object[] args;
    public Object target;
    private Method targetMethod;
    private Method originalMethod;
    private LightMethod targetLightMethod;
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return proceed(args);
    }
    public Object proceed(Object[] args) throws InvocationTargetException, IllegalAccessException {
        return targetMethod.invoke(target,args);
    }

    public LightMethod getTargetMethod() {
        return targetLightMethod;
    }

    void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    void setOriginalMethod(Method originalMethod) {
        this.originalMethod = originalMethod;
        targetLightMethod = new LightMethod();
        targetLightMethod.setOriginalMethod(originalMethod);
    }

    public Object getTarget() {
        return target;
    }
}
