package com.flyjingfish.light_aop_plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

public class AnnotationMethodScanner extends ClassVisitor {
    Logger logger;
    private final OnCallBackMethod onCallBackMethod;
    public AnnotationMethodScanner(Logger logger,OnCallBackMethod onCallBackMethod) {
        super(Opcodes.ASM8);
        this.logger = logger;
        this.onCallBackMethod = onCallBackMethod;
    }


    private boolean isDescendantClass;
    private AopMatchCut aopMatchCut;
    private List<MethodRecord> cacheMethodRecords = new ArrayList<>();
    private String className;
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        if (superName != null){

            WovenInfoUtils.INSTANCE.getAopMatchCuts().forEach((key,aopMatchCut)->{
//                try{
//                    ClassPool cp = new ClassPool(null);
//                    cp.appendSystemPath();
//                    for (String classPath : WovenInfoUtils.INSTANCE.getClassPaths()) {
//                        cp.appendClassPath(classPath);
//                    }
//
//                    CtClass superClass = cp.getCtClass(Utils.INSTANCE.slashToDot(superName));
//                    int index = 0;
//                    do {
//                        if (aopMatchCut.getBaseClassName().equals(superClass.getName())) {
//                            this.isDescendantClass= true;
//                            AnnotationMethodScanner.this.aopMatchCut = aopMatchCut;
//
//                            break;
//                        }
//                        if (index > 3){
//                            break;
//                        }
//                        index++;
//                        superClass = superClass.getSuperclass();
//                    } while (superClass != null);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//        ClassPool cp = ClassPool.getDefault();


                if (aopMatchCut.getBaseClassName().equals(Utils.INSTANCE.slashToDot(superName))) {
                    this.isDescendantClass= true;
                    AnnotationMethodScanner.this.aopMatchCut = aopMatchCut;
                }
            });
        }
        super.visit(version, access, name, signature, superName, interfaces);

    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    class MyMethodVisitor extends MethodVisitor {
        MethodRecord methodName;
        MyMethodVisitor(MethodRecord methodName) {
            super(Opcodes.ASM8);
            this.methodName = methodName;
        }
        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//            logger.error("AnnotationMethodScanner MyMethodVisitor type: " + descriptor);
            if (WovenInfoUtils.INSTANCE.isContainAnno(descriptor)){
                boolean isBack = true;
//                try {
//                    ClassPool cp = new ClassPool(null);
//                    cp.appendSystemPath();
//                    for (String classPath : WovenInfoUtils.INSTANCE.getClassPaths()) {
//                        cp.appendClassPath(classPath);
//                    }
//                    CtClass ctClass = cp.getCtClass(Utils.INSTANCE.slashToDot(className));
//                    CtMethod ctMethod = getCtMethod(ctClass,methodName.getMethodName(),methodName.getDescriptor());
//                    MethodInfo methodInfo = ctMethod.getMethodInfo();
//                    CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//                    if (codeAttribute == null){
//                        isBack = false;
//                    }
//                } catch (NotFoundException e) {
//                    throw new RuntimeException(e);
//                }
                if (onCallBackMethod != null && isBack){
                    onCallBackMethod.onBackName(methodName);
                }
            }
            return super.visitAnnotation(descriptor, visible);
        }
    }
    public static CtMethod getCtMethod(CtClass ctClass,String methodName,String descriptor) throws NotFoundException {
        CtMethod[] ctMethods = ctClass.getDeclaredMethods(methodName);
        if (ctMethods != null && ctMethods.length>0){
            for (CtMethod ctMethod : ctMethods) {
                String allSignature = ctMethod.getSignature();
                if (descriptor.equals(allSignature)){
                    return ctMethod;
                }
            }
        }
        return null;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        if (isDescendantClass){
            logger.error("AnnotationMethodScanner method: name = " + name);
            for (String methodName : aopMatchCut.getMethodNames()) {
                if (methodName.equals(name)){
                    boolean isBack = true;
//                    try {
//                        ClassPool cp = new ClassPool(null);
//                        cp.appendSystemPath();
//                        for (String classPath : WovenInfoUtils.INSTANCE.getClassPaths()) {
//                            cp.appendClassPath(classPath);
//                        }
//                        CtClass ctClass = cp.getCtClass(Utils.INSTANCE.slashToDot(className));
//                        CtMethod ctMethod = getCtMethod(ctClass,name,descriptor);
//                        MethodInfo methodInfo = ctMethod.getMethodInfo();
//                        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//                        if (codeAttribute == null){
//                            isBack = false;
//                        }
//                    } catch (NotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
                    if (isBack){
                        cacheMethodRecords.add(new MethodRecord(name,descriptor, aopMatchCut.getCutClassName()));
                    }
                    break;
                }
            }
        }
        return new MyMethodVisitor(new MethodRecord(name,descriptor,null));
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        if (isDescendantClass){
            logger.error("AnnotationMethodScanner visitEnd");
            for (MethodRecord cacheMethodRecord : cacheMethodRecords) {
                if (onCallBackMethod != null){
                    onCallBackMethod.onBackName(cacheMethodRecord);
                }
            }
        }
    }

    public interface OnCallBackMethod{
        void onBackName(MethodRecord methodRecord);
    }
}