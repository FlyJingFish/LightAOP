package com.flyjingfish.light_aop_processor

import com.flyjingfish.light_aop_annotation.LightAop
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

class LightAopProcessor : AbstractProcessor() {
    private fun isEmpty(coll: Collection<*>?): Boolean {
        return coll == null || coll.isEmpty()
    }
    override fun process(set: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        val elements: Set<Element> = roundEnvironment.getElementsAnnotatedWith(
            LightAop::class.java
        )
        if (!isEmpty(elements)) {
            for (element in elements) {
                val packageStr = element.enclosedElements.toString()
                val classStr = element.simpleName.toString()
                val service: LightAop = element.getAnnotation(LightAop::class.java)
                val constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageStr, classStr), "activity")
            }
        }

        for (element in roundEnvironment.rootElements) {
            val packageStr = element.enclosedElements.toString()
            val classStr = element.simpleName.toString()
            val className = ClassName.get(packageStr, classStr + "Binding")
            val constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(packageStr, classStr), "activity")
            var hasBinding = false
            for (enclosedElement in element.enclosedElements) {
                if (enclosedElement.kind == ElementKind.TYPE_PARAMETER) {
                    val bindView = enclosedElement.getAnnotation(LightAop::class.java)
                    if (bindView != null) {
                        hasBinding = true
//                        constructorBuilder.addStatement(
//                            "activity.SN=activity.findViewById(SL)",
//                            enclosedElement.getSimpleName(), bindView.value()
//                        )
//                        TypeSpec builtClass = TypeSpec . classBuilder (className)
//                            .addModifiers(Modifier.PUBLIC)
//                            .addMethod(constructorBuilder.build())
//                            .build()
//                        if (hasBinding) {
//                            try {
//                                JavaFile.builder(packageStr, builtClass)
//                            }
//                        }
                    }
                }
            }
        }
        return true
    }
}