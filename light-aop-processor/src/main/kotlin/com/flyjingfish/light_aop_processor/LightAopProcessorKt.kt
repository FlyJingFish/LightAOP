package com.flyjingfish.light_aop_processor

import com.flyjingfish.light_aop_annotation.LightAopMatchClassMethod
import com.flyjingfish.light_aop_annotation.LightAopPointCut
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.io.File
import java.io.IOException
import java.lang.annotation.Target
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException

class LightAopProcessorKt: AbstractProcessor() {
    var mFiler: Filer? = null
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
    override fun getSupportedAnnotationTypes(): Set<String> {
        val set: MutableSet<String> = LinkedHashSet()
        set.add(LightAopPointCut::class.java.canonicalName)
        set.add(LightAopMatchClassMethod::class.java.canonicalName)
        return set
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    private fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    fun isEmpty(set: Set<*>?): Boolean {
        return set == null || set.isEmpty()
    }

    fun isNotEmpty(map: Map<*, *>?): Boolean {
        return !isEmpty(map)
    }

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        mFiler = processingEnv.filer
    }

    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        println("======LightAopProcessorJava======" + set.size)
        if (isEmpty(set)) {
            return false
        }
        processPointCut(set, roundEnvironment)
        processMatch(set, roundEnvironment)
        return false
    }

    private fun processPointCut(set: Set<TypeElement>, roundEnvironment: RoundEnvironment) {
        val elements = roundEnvironment.getElementsAnnotatedWith(
            LightAopPointCut::class.java
        )
        for (typeElement in set) {
            val name = typeElement.simpleName
            for (element in elements) {
                val name1 = element.simpleName
                //                System.out.println("======"+name);
//                System.out.println("======"+element);
                val cut = element.getAnnotation(LightAopPointCut::class.java)
                val target = element.getAnnotation(
                    Target::class.java
                )
                val className: String? = try {
                    cut.value.qualifiedName
                } catch (mirroredTypeException: MirroredTypeException) {
                    val errorMessage = mirroredTypeException.localizedMessage
                    errorMessage.substring(errorMessage.lastIndexOf(" ") + 1)
                    //                    System.out.printf("%n成功转换出类型：%s%n", className);
                }
                //                System.out.println("===cut==="+className);
//                System.out.println("===target==="+target.value()[0]);
                val typeBuilder = TypeSpec.classBuilder(
                    "${name1}AutoAspectJ"
                ).addModifiers(KModifier.FINAL)
                    .addAnnotation(Aspect::class)
                val whatsMyName1 = whatsMyName("withinAnnotatedClass")
                    .addAnnotation(
                        AnnotationSpec.builder(Pointcut::class)
                            .addMember("value = %S", "within(@$element *)")
                            .build()
                    )
                val whatsMyName2 = whatsMyName("methodInsideAnnotatedType")
                    .addAnnotation(
                        AnnotationSpec.builder(Pointcut::class)
                            .addMember(
                                "value = %S",
                                "execution(!synthetic * *(..)) && withinAnnotatedClass()"
                            )
                            .build()
                    )
                val whatsMyName3 = whatsMyName("constructorInsideAnnotatedType")
                    .addAnnotation(
                        AnnotationSpec.builder(Pointcut::class)
                            .addMember(
                                "value = %S",
                                "execution(!synthetic *.new(..)) && withinAnnotatedClass()"
                            )
                            .build()
                    )
                val whatsMyName4 = whatsMyName("method")
                    .addAnnotation(
                        AnnotationSpec.builder(Pointcut::class)
                            .addMember(
                                "value = %S",
                                "execution(@$element * *(..)) || methodInsideAnnotatedType()"
                            )
                            .build()
                    )
                val whatsMyName5 = whatsMyName("constructorJava")
                    .addAnnotation(
                        AnnotationSpec.builder(Pointcut::class)
                            .addMember(
                                "value = %S",
                                "execution(@$element *.new(..)) || constructorInsideAnnotatedType()"
                            )
                            .build()
                    )
                val elementName = element.toString()
                val packageName = elementName.substring(0, elementName.lastIndexOf("."))
                val simpleName = elementName.substring(elementName.lastIndexOf(".") + 1)
                val valueName = "v$simpleName"

//                System.out.println("===packageName==="+packageName);
//                System.out.println("===simpleName==="+simpleName);
                val whatsMyName6 = whatsMyName("cutExecute")
                    .addParameter("joinPoint",ProceedingJoinPoint::class)
                    .addParameter(valueName,ClassName.bestGuess("$packageName.$simpleName"))
                    .addAnnotation(
                        AnnotationSpec.builder(Around::class)
                            .addMember(
                                "value = %S",
                                "(method() || constructorJava()) && @annotation($valueName)"
                            )
                            .build()
                    )

//                whatsMyName6.addStatement("com.flyjingfish.light_aop_annotation.BaseLightAop baseLightAop = $T.INSTANCE.getBaseLightAop($R,$S)", ClassName.get("com.flyjingfish.light_aop_core.utils","LightAopBeanUtils"), ParameterSpec.builder(ProceedingJoinPoint.class, "joinPoint").build(), className);
                whatsMyName6.addStatement(
                    "val baseLightAop :com.flyjingfish.light_aop_annotation.BasePointCut<$simpleName> = %T.getBasePointCut(joinPoint,%S)as com.flyjingfish.light_aop_annotation.BasePointCut<$simpleName>",
                    ClassName.bestGuess("com.flyjingfish.light_aop_core.utils.LightAopBeanUtils"),
                    className!!
                )
                whatsMyName6.addStatement("val result = baseLightAop.invoke(joinPoint,$valueName)")
                whatsMyName6.returns(Any::class.asTypeName().copy(nullable = true)).addStatement("return result")
                typeBuilder.addFunction(whatsMyName1.build())
                typeBuilder.addFunction(whatsMyName2.build())
                typeBuilder.addFunction(whatsMyName3.build())
                typeBuilder.addFunction(whatsMyName4.build())
                typeBuilder.addFunction(whatsMyName5.build())
                typeBuilder.addFunction(whatsMyName6.build())
                val typeSpec = typeBuilder.build()
                val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME];
                val javaFile = FileSpec.builder("com.flyjingfish.light_aop_core.acpectj","${name1}AutoAspectJ").addType(typeSpec)
                    .build()
                try {
                    javaFile.writeTo(File(kaptKotlinGeneratedDir!!))
                } catch (e: IOException) {
//                    throw new RuntimeException(e);
                }
            }
        }
    }

    private fun processMatch(set: Set<TypeElement>, roundEnvironment: RoundEnvironment) {
        val elements = roundEnvironment.getElementsAnnotatedWith(
            LightAopMatchClassMethod::class.java
        )
        //        System.out.println("======processMatch======"+elements.size());
        for (typeElement in set) {
            val name = typeElement.simpleName
            for (element in elements) {
                val name1 = element.simpleName
                println("======$name")
                println("======$element")
                val matchClassName = element.toString()
                val cut = element.getAnnotation(
                    LightAopMatchClassMethod::class.java
                )
                val methodNames: Array<String> = cut.methodName
                val targetClassName: String = cut.targetClassName
                //                System.out.println("===cut==="+targetClassName);
//                System.out.println("===target==="+target.value()[0]);
                val typeBuilder = TypeSpec.classBuilder(
                    "${name1}AutoAspectJ"
                ).addModifiers(KModifier.FINAL)
                    .addAnnotation(Aspect::class)
                val methodSpecBuilders: MutableList<FunSpec.Builder> = ArrayList()
                val whatsMyNames: MutableList<String> = ArrayList()
                for (methodName in methodNames) {
                    val whatsMyName = "targetMethod_$methodName"
                    val whatsMyName1 = whatsMyName(whatsMyName)
                        .addAnnotation(
                            AnnotationSpec.builder(Pointcut::class)
                                .addMember(
                                    "value = %S",
                                    "execution(* $targetClassName+.$methodName(..))"
                                )
                                .build()
                        )
                    methodSpecBuilders.add(whatsMyName1)
                    whatsMyNames.add(whatsMyName)
                }
                val stringBuffer = StringBuffer()
                stringBuffer.append("(")
                //                (method() || constructor())
                for (i in whatsMyNames.indices) {
                    val whatsMyName = whatsMyNames[i]
                    stringBuffer.append(whatsMyName).append("()")
                    if (i != whatsMyNames.size - 1) {
                        stringBuffer.append(" || ")
                    }
                }
                stringBuffer.append(")")
                val elementName = element.toString()
                val packageName = elementName.substring(0, elementName.lastIndexOf("."))
                val simpleName = elementName.substring(elementName.lastIndexOf(".") + 1)
                val valueName = "v$simpleName"

//                System.out.println("===packageName==="+packageName);
//                System.out.println("===simpleName==="+simpleName);
                val whatsMyName2 = whatsMyName("cutExecute")
                    .addParameter("joinPoint",ProceedingJoinPoint::class)
                    .addAnnotation(
                        AnnotationSpec.builder(Around::class)
                            .addMember("value = %S", stringBuffer.toString())
                            .build()
                    )
                whatsMyName2.addStatement(
                    "val matchClassMethod : com.flyjingfish.light_aop_annotation.MatchClassMethod = %T.getMatchClassMethod(joinPoint,%S)",
                    ClassName.bestGuess("com.flyjingfish.light_aop_core.utils.LightAopBeanUtils"),
                    element.toString()
                )
                whatsMyName2.addStatement("val result = matchClassMethod.invoke(joinPoint,joinPoint.getSignature().getName())")
                whatsMyName2.returns(Any::class.asTypeName().copy(nullable = true)).addStatement("return result")
                for (methodSpecBuilder in methodSpecBuilders) {
                    typeBuilder.addFunction(methodSpecBuilder.build())
                }
                typeBuilder.addFunction(whatsMyName2.build())

                val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME];
                val typeSpec = typeBuilder.build()
                val javaFile = FileSpec.builder("com.flyjingfish.light_aop_core.acpectj","${name1}AutoAspectJ").addType(typeSpec)
                    .build()
                try {
                    javaFile.writeTo(File(kaptKotlinGeneratedDir!!))
                } catch (e: IOException) {
//                    throw new RuntimeException(e);
                }
            }
        }
    }


    private fun whatsMyName(name: String): FunSpec.Builder {
        return FunSpec.builder(name).addModifiers(KModifier.FINAL)
    }
}