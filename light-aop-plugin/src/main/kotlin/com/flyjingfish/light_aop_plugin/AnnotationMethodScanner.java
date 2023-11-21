package com.flyjingfish.light_aop_plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;

public class AnnotationMethodScanner extends ClassVisitor {
    Logger logger;
    private final OnCallBackMethod onCallBackMethod;
    public AnnotationMethodScanner(Logger logger,OnCallBackMethod onCallBackMethod) {
        super(Opcodes.ASM8);
        this.logger = logger;
        this.onCallBackMethod = onCallBackMethod;
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
//            logger.error("AnnotationMethodScanner MethodVisitor type: " + descriptor);
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
//        logger.error("AnnotationMethodScanner method: name = " + name);
        return new MyMethodVisitor(new MethodRecord(name,descriptor));
    }

    public interface OnCallBackMethod{
        void onBackName(MethodRecord methodRecord);
    }
}