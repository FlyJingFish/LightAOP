package com.flyjingfish.light_aop_plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;

public class AnnotationScanner extends ClassVisitor {
    Logger logger;
    static final String CLASS_POINT = "Lcom/flyjingfish/light_aop_annotation/LightAopClass";
    static final String METHOD_POINT = "Lcom/flyjingfish/light_aop_annotation/LightAopMethod";
    boolean isLightAopClass;
    public AnnotationScanner(Logger logger) {
        super(Opcodes.ASM8);
        this.logger = logger;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        logger.error("annotation ClassVisitor type: " + descriptor);
        if (descriptor.contains(CLASS_POINT)){
            isLightAopClass = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    class MethodAnnoVisitor extends AnnotationVisitor {
        String anno;
        String cutClassName;
        MethodAnnoVisitor() {
            super(Opcodes.ASM8);
        }
        @Override
        public void visit(String name, Object value) {
            if (isLightAopClass){
                logger.error("annotation: " + name + " = " + value);
                if (name.equals("value")) {
                    anno = value.toString();
                }
                if (name.equals("pointCutClassName")) {
                    cutClassName = value.toString();
                }
//                WovenInfoUtils.INSTANCE.addAnnoInfo(value.toString());
            }
            super.visit(name, value);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            AopMethodCut cut = new AopMethodCut(anno,cutClassName);
            WovenInfoUtils.INSTANCE.addAnnoInfo(cut);
        }
    }

    class MyMethodVisitor extends MethodVisitor {
        MyMethodVisitor() {
            super(Opcodes.ASM8);
        }
        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (isLightAopClass){
                logger.error("annotation MethodVisitor type: " + descriptor);
                if (descriptor.contains(METHOD_POINT)){
                    return new MethodAnnoVisitor();
                }else {
                    return super.visitAnnotation(descriptor, visible);
                }
            }else {
                return super.visitAnnotation(descriptor, visible);
            }
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        if (isLightAopClass){
            logger.error("method: name = " + name+",desc="+descriptor+",signature="+signature);
            return new MyMethodVisitor();
        }else {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }
}