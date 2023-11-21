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
import javassist.NotFoundException;

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
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
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
                if (onCallBackMethod != null){
                    onCallBackMethod.onBackName(methodName);
                }
            }
            return super.visitAnnotation(descriptor, visible);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        if (isDescendantClass){
            logger.error("AnnotationMethodScanner method: name = " + name);
            for (String methodName : aopMatchCut.getMethodNames()) {
                if (methodName.equals(name)){
                    cacheMethodRecords.add(new MethodRecord(name,descriptor, aopMatchCut.getCutClassName()));
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