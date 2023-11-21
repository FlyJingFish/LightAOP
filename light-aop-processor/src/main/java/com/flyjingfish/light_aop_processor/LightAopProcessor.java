package com.flyjingfish.light_aop_processor;

import com.flyjingfish.light_aop_annotation.LightAopClass;
import com.flyjingfish.light_aop_annotation.LightAopMatch;
import com.flyjingfish.light_aop_annotation.LightAopMatchClassMethod;
import com.flyjingfish.light_aop_annotation.LightAopMethod;
import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

public class LightAopProcessor extends AbstractProcessor {
    Filer mFiler;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(LightAopPointCut.class.getCanonicalName());
        set.add(LightAopMatchClassMethod.class.getCanonicalName());
        return set;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }
    public static boolean isEmpty(final Set<?> set) {
        return set == null || set.isEmpty();
    }
    public static boolean isNotEmpty(final Map<?,?> map) {
        return !isEmpty(map);
    }
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("======LightAopProcessorJava======"+set.size());
        if (isEmpty(set)){
            return false;
        }
        processPointCut(set, roundEnvironment);
        processMatch(set, roundEnvironment);
        return false;
    }

    public void processPointCut(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LightAopPointCut.class);
        for (TypeElement typeElement: set){
            Name name = typeElement.getSimpleName();
            for (Element element : elements) {
                Name name1 = element.getSimpleName();
//                System.out.println("======"+name);
//                System.out.println("======"+element);
                LightAopPointCut cut = element.getAnnotation(LightAopPointCut.class);
                Target target = element.getAnnotation(Target.class);
                String className;
                try{
                    className = cut.value().getName();
                }catch (MirroredTypeException mirroredTypeException){
                    String errorMessage = mirroredTypeException.getLocalizedMessage();
                    className = errorMessage.substring( errorMessage.lastIndexOf(" ")+1);
//                    System.out.printf("%n成功转换出类型：%s%n", className);
                }
//                System.out.println("===cut==="+className);
//                System.out.println("===target==="+target.value()[0]);
                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(name1+"$$LightAopClass")
                        .addAnnotation(LightAopClass.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
                MethodSpec.Builder whatsMyName1 = whatsMyName("withinAnnotatedClass")
                        .addAnnotation(AnnotationSpec.builder(LightAopMethod.class)
                                .addMember("value", "$S", "@"+element)
                                .addMember("pointCutClassName", "$S", className)
                                .build());

                typeBuilder.addMethod(whatsMyName1.build());

                TypeSpec typeSpec = typeBuilder.build();

                JavaFile javaFile = JavaFile.builder("com.flyjingfish.light_aop_core.light_aop_class", typeSpec)
                        .build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
//                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void processMatch(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LightAopMatchClassMethod.class);
        for (TypeElement typeElement: set){
            Name name = typeElement.getSimpleName();
            for (Element element : elements) {
                Name name1 = element.getSimpleName();
//                System.out.println("======"+name);
//                System.out.println("======"+element);
                LightAopMatchClassMethod cut = element.getAnnotation(LightAopMatchClassMethod.class);
                Target target = element.getAnnotation(Target.class);
                String className = cut.targetClassName();
                String[] methodNames = cut.methodName();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < methodNames.length; i++) {
                    stringBuilder.append(methodNames[i]);
                    if (i != methodNames.length -1){
                        stringBuilder.append("-");
                    }
                }
                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(name1+"$$LightAopClass")
                        .addAnnotation(LightAopClass.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
                MethodSpec.Builder whatsMyName1 = whatsMyName("withinAnnotatedClass")
                        .addAnnotation(AnnotationSpec.builder(LightAopMatch.class)
                                .addMember("baseClassName", "$S", className)
                                .addMember("methodNames", "$S", stringBuilder)
                                .build());

                typeBuilder.addMethod(whatsMyName1.build());

                TypeSpec typeSpec = typeBuilder.build();

                JavaFile javaFile = JavaFile.builder("com.flyjingfish.light_aop_core.light_aop_class", typeSpec)
                        .build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
//                    throw new RuntimeException(e);
                }
            }
        }
    }


    private static MethodSpec.Builder whatsMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }
}
