package com.flyjingfish.light_aop_processor;

import com.flyjingfish.light_aop_annotation.LightAopClass;
import com.flyjingfish.light_aop_annotation.LightAopMethod;
import com.flyjingfish.light_aop_annotation.LightAopPointCut;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.LinkedHashSet;
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
//        set.add(LightAopMatchClassMethod.class.getCanonicalName());
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
//        processMatch(set, roundEnvironment);
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

//    public void processMatch(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LightAopMatchClassMethod.class);
////        System.out.println("======processMatch======"+elements.size());
//        for (TypeElement typeElement: set){
//            Name name = typeElement.getSimpleName();
//            for (Element element : elements) {
//                Name name1 = element.getSimpleName();
//                System.out.println("======"+name);
//                System.out.println("======"+element);
//                String matchClassName = element.toString();
//                LightAopMatchClassMethod cut = element.getAnnotation(LightAopMatchClassMethod.class);
//                String[] methodNames = cut.methodName();
//                String targetClassName = cut.targetClassName();
////                System.out.println("===cut==="+targetClassName);
////                System.out.println("===target==="+target.value()[0]);
//                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(name1+"$$AspectJ")
//                        .addAnnotation(Aspect.class)
//                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
//                final List<MethodSpec.Builder> methodSpecBuilders = new ArrayList<>();
//                final List<String> whatsMyNames = new ArrayList<>();
//                for (String methodName : methodNames) {
//                    String whatsMyName = "targetMethod_" + methodName;
//                    MethodSpec.Builder whatsMyName1 = whatsMyName(whatsMyName)
//                            .addAnnotation(AnnotationSpec.builder(Pointcut.class)
//                                    .addMember("value", "$S", "execution(* "+targetClassName+"+."+methodName+"(..))")
//                                    .build());
//                    methodSpecBuilders.add(whatsMyName1);
//                    whatsMyNames.add(whatsMyName);
//                }
//                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("(");
////                (method() || constructor())
//                for (int i = 0; i < whatsMyNames.size(); i++) {
//                    String whatsMyName = whatsMyNames.get(i);
//                    stringBuffer.append(whatsMyName).append("()");
//                    if (i != whatsMyNames.size() - 1){
//                        stringBuffer.append(" || ");
//                    }
//                }
//                stringBuffer.append(")");
//                String elementName = element.toString();
//                String packageName = elementName.substring(0,elementName.lastIndexOf("."));
//                String simpleName = elementName.substring(elementName.lastIndexOf(".")+1);
//                String valueName = "v"+simpleName;
//
////                System.out.println("===packageName==="+packageName);
////                System.out.println("===simpleName==="+simpleName);
//                MethodSpec.Builder whatsMyName2 = whatsMyName("cutExecute")
//                        .addParameter(ProceedingJoinPoint.class,"joinPoint",Modifier.FINAL)
//                        .addAnnotation(AnnotationSpec.builder(Around.class)
//                                .addMember("value", "$S",stringBuffer.toString())
//                                .build());
//
//                whatsMyName2.addStatement("com.flyjingfish.light_aop_annotation.MatchClassMethod matchClassMethod = $T.INSTANCE.getMatchClassMethod(joinPoint,$S)", ClassName.get("com.flyjingfish.light_aop_core.utils","LightAopBeanUtils"),element.toString());
//                whatsMyName2.addStatement("Object result = matchClassMethod.invoke(joinPoint,joinPoint.getSignature().getName())");
//                whatsMyName2.returns(Object.class).addStatement("return result");
//                for (MethodSpec.Builder methodSpecBuilder : methodSpecBuilders) {
//                    typeBuilder.addMethod(methodSpecBuilder.build());
//                }
//                typeBuilder.addMethod(whatsMyName2.build());
//
//                TypeSpec typeSpec = typeBuilder.build();
//
//                JavaFile javaFile = JavaFile.builder("com.flyjingfish.light_aop_core.acpectj", typeSpec)
//                        .build();
//                try {
//                    javaFile.writeTo(mFiler);
//                } catch (IOException e) {
////                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }


    private static MethodSpec.Builder whatsMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }
}
