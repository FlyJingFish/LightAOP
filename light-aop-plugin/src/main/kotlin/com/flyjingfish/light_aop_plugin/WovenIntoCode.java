package com.flyjingfish.light_aop_plugin;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ASM4;


import com.flyjingfish.light_aop_annotation.Conversions;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

public class WovenIntoCode {
    public static byte[] modifyClass(byte[] inputStreamBytes, HashMap<String, MethodRecord> methodRecordHashMap) throws Exception {
        ClassReader cr = new ClassReader(inputStreamBytes);
        final ClassWriter cw = new ClassWriter(cr, 0);
        cr.accept(new ClassVisitor(ASM4, cw) {}, 0);
        methodRecordHashMap.forEach((key, value) ->{
            String oldMethodName = value.getMethodName();
            String targetMethodName = oldMethodName+"LightAopAutoMethod";
            String oldDescriptor = value.getDescriptor();

            cr.accept(new ClassVisitor(ASM4, cw) {
                @Override
                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                    System.out.println("annotation ClassVisitor type: " + descriptor);
                    return new AnnotationVisitor(Opcodes.ASM8) {
                        @Override
                        public AnnotationVisitor visitAnnotation(String name, String descriptor) {
                            System.out.println("annotation: " + name + " = " + value);
                            return super.visitAnnotation(name, descriptor);
                        }
                    };
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    System.out.println("visit method:" + name + "====> " + descriptor);
                    if (oldMethodName.equals(name) && oldDescriptor.equals(descriptor)) {
                        return super.visitMethod(ACC_PUBLIC, targetMethodName, descriptor, signature, exceptions);
                    }else {
                        return null;
                    }

                }

                @Override
                public void visitEnd() {
                    super.visitEnd();//注意原本的visiEnd不能少
                }

            }, 0);
        });

        ClassPool cp = new ClassPool(null);
        cp.appendSystemPath();
//        ClassPool cp = ClassPool.getDefault();
        InputStream byteArrayInputStream = new ByteArrayInputStream(cw.toByteArray());
        CtClass ctClass = cp.makeClass(byteArrayInputStream);
        cp.importPackage("com.flyjingfish.light_aop_annotation.LightAopJoinPoint");
        cp.importPackage("com.flyjingfish.light_aop_annotation.Conversions");
        cp.importPackage("androidx.annotation.Keep");


        methodRecordHashMap.forEach((key, value) ->{
            String oldMethodName = value.getMethodName();
            String targetMethodName = oldMethodName+"LightAopAutoMethod";
            String oldDescriptor = value.getDescriptor();

            try {
                CtMethod ctMethod = getCtMethod(ctClass,oldMethodName,oldDescriptor);
                CtMethod targetMethod = getCtMethod(ctClass,targetMethodName,oldDescriptor);


                ClassFile ccFile = ctClass.getClassFile();
                ConstPool constpool = ccFile.getConstPool();

                // create the annotation
                AnnotationsAttribute annotAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
                Annotation annot = new Annotation("androidx.annotation.Keep", constpool);
                annotAttr.addAnnotation(annot);

                targetMethod.getMethodInfo().addAttribute(annotAttr);

                List<String> paramNames = new ArrayList<>();
                MethodInfo methodInfo = ctMethod.getMethodInfo();
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                StringBuffer argsBuffer = new StringBuffer();
                boolean isStaticMethod = Modifier.isStatic(ctMethod.getModifiers());
                boolean isHasArgs = false;
                if (attr != null) {
                    int len = ctMethod.getParameterTypes().length;
                    // 非静态的成员函数的第一个参数是this
                    int pos = isStaticMethod ? 0 : 1;
                    isHasArgs = len>0;
                    for (int i = 0; i < len; i++) {
                        int index = i + pos;
                        String signature = attr.signature(i + pos);
                        System.out.println("signature="+attr.signature(i + pos));
                        argsBuffer.append(String.format(Conversions.getArgsXObject(signature),"$"+index));
                        if (i != len -1){
                            argsBuffer.append(",");
                        }
                        paramNames.add(attr.variableName(i + pos));
                    }
                }
                String allSignature = ctMethod.getSignature();

                String returnStr = String.format(Conversions.getReturnXObject(allSignature.substring(allSignature.indexOf(")")+1)),"pointCut.joinPointExecute()");
                System.out.println(allSignature.substring(allSignature.indexOf(")")+1));
                System.out.println(returnStr);
                System.out.println(paramNames);
                System.out.println(argsBuffer);
                System.out.println(isHasArgs);
                String body = " {LightAopJoinPoint pointCut = new LightAopJoinPoint("+(isStaticMethod?"$0":"$0,"+"\""+oldMethodName+"\","+"\""+targetMethodName+"\"")+");\n" +
                        (isHasArgs? "        Object[] args = new Object[]{"+argsBuffer+"};\n" :"")+
                        (isHasArgs? "        pointCut.setArgs(args);\n" :"        pointCut.setArgs(null);\n")+
                        "        "+returnStr+";}";
                System.out.println(body);
                ctMethod.setBody(body);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
        byte[] classByteCode = ctClass.toBytecode();
        return classByteCode;
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
}
