package com.flyjingfish.light_aop_plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;

public class AnnotationMethodScanner extends ClassVisitor {
    Logger logger;
    private OnCallBackMethod onCallBackMethod;
    public AnnotationMethodScanner(Logger logger,OnCallBackMethod onCallBackMethod) {
        super(Opcodes.ASM8);
        this.logger = logger;
        this.onCallBackMethod = onCallBackMethod;
    }
//    public AnnotationScanner() {
//        super(Opcodes.ASM8);
//    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

//    class MethodAnnoVisitor extends AnnotationVisitor {
//        String methodName;
//        MethodAnnoVisitor(String methodName) {
//            super(Opcodes.ASM8);
//            this.methodName = methodName;
//        }
//        @Override
//        public void visit(String name, Object value) {
//            if (WovenInfoUtils.INSTANCE.isContainAnno(value.toString())){
//                if (onCallBackMethod != null){
//                    onCallBackMethod.onBackName(methodName);
//                }
//            }
//            super.visit(name, value);
//        }
//    }

    class MyMethodVisitor extends MethodVisitor {
        String methodName;
        MyMethodVisitor(String methodName) {
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
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
//        logger.error("AnnotationMethodScanner method: name = " + name);
        return new MyMethodVisitor(name);
    }

    public interface OnCallBackMethod{
        void onBackName(String methodName);
    }
}