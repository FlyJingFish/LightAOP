package com.flyjingfish.light_aop_annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class LightAopJoinPoint {
    public Object joinPointExecute(){
        System.out.println("------joinPointExecute-----");
        ProceedJoinPoint proceedJoinPoint = new ProceedJoinPoint();
        proceedJoinPoint.target = target;
        proceedJoinPoint.args = mArgs;
        proceedJoinPoint.setOriginalMethod(originalMethod);
        proceedJoinPoint.setTargetMethod(targetMethod);
        Annotation[] annotations = originalMethod.getAnnotations();
        Object returnValue = null;
        for (Annotation annotation : annotations) {
            String cutClassName = JoinAnnoCutUtils.getCutClassName(annotation.annotationType().getName());
            if (cutClassName != null){
                BasePointCut<Annotation> basePointCut = LightAopBeanUtils.INSTANCE.getBasePointCut(proceedJoinPoint,cutClassName);
                if (basePointCut != null){
                    returnValue = basePointCut.invoke(proceedJoinPoint,annotation);
                }
            }
        }

        return returnValue;
    }
    private Object target;
    private Object[] mArgs;
    private String targetMethodName;
    private String originalMethodName;
    private Method targetMethod;
    private Method originalMethod;

    public LightAopJoinPoint(Object target,String originalMethodName, String targetMethodName) {
        this.target = target;
        this.originalMethodName = originalMethodName;
        this.targetMethodName = targetMethodName;
    }

    public Object[] getArgs() {
        return mArgs;
    }

    public void setArgs(Object[] args) {
        this.mArgs = args;
        try {
            Class[] classes = null;
            if (args != null && args.length > 0){
                classes = new Class[args.length];
                int index = 0;
                for (Object arg : args) {
                    classes[index] = arg.getClass();
                    index++;
                }
            }
            if (classes != null){
                targetMethod = target.getClass().getDeclaredMethod(targetMethodName,classes);
                originalMethod = target.getClass().getDeclaredMethod(originalMethodName,classes);
            }else {
                targetMethod = target.getClass().getDeclaredMethod(targetMethodName);
                originalMethod = target.getClass().getDeclaredMethod(originalMethodName);
            }
            targetMethod.setAccessible(true);
            originalMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
