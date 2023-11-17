package com.flyjingfish.light_aop_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.gradle.util.GradleVersion
import java.io.File
import java.util.Locale

class LightAopPlugin : Plugin<Project> {
    private companion object {
        private const val ANDROID_EXTENSION_NAME = "android"
    }
    override fun apply(project: Project) {
        val isApp = project.plugins.hasPlugin(AppPlugin::class.java)
        val isLib = project.plugins.hasPlugin(LibraryPlugin::class.java)
        if (!isApp && !isLib) {
            throw IllegalStateException("'android' or 'android-library' plugin required.")
        }
        val log = project.logger

        val dependencyGav = "org.aspectj:aspectjrt:1.9.8"

        if (GradleVersion.current() > GradleVersion.version("4.0")) {
            project.dependencies.add("implementation", dependencyGav)
        } else {
            project.dependencies.add("compile", dependencyGav)
        }

        val isDynamicLibrary = project.plugins.hasPlugin(DynamicFeaturePlugin::class.java)

        val android = project.extensions.findByName(ANDROID_EXTENSION_NAME) as BaseExtension
        val variants = if (isApp or isDynamicLibrary) {
            (android as AppExtension).applicationVariants
        } else {
            (android as LibraryExtension).libraryVariants
        }

        variants.all { variant ->
            variant.outputs.all { output ->
                val javaCompile: AbstractCompile =
                    if (DefaultGroovyMethods.hasProperty(variant, "javaCompileProvider") != null) {
                        //gradle 4.10.1 +
                        variant.javaCompileProvider.get()
                    } else if (DefaultGroovyMethods.hasProperty(variant, "javaCompiler") != null) {
                        variant.javaCompiler as AbstractCompile
                    } else {
                        variant.javaCompile as AbstractCompile
                    }
                var fullName = ""
                val names = output.name.split("-");
                for((index,token) in names.withIndex()){
                    if ("" != token){
                        fullName += (if (index == 0) token else token.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        })
                    }
                }
                javaCompile.doLast {
                    val javaArgs = mutableListOf(
                        "-showWeaveInfo",
                        "-1.9",
                        "-inpath",
                        javaCompile.destinationDirectory.asFile.getOrNull().toString(),
                        "-aspectpath",
                        javaCompile.classpath.asPath,
                        "-d",
                        javaCompile.destinationDirectory.asFile.getOrNull().toString(),
                        "-classpath",
                        javaCompile.classpath.asPath,
                        "-bootclasspath",
                        android.bootClasspath.joinToString(File.pathSeparator)
                    )
                    log.info("ajc javaArgs: " + javaArgs.toTypedArray())
                    val kotlinArgs = mutableListOf(
                        "-showWeaveInfo",
                        "-1.9",
                        "-inpath",
                        project.buildDir.path + "/tmp/kotlin-classes/" + fullName,
                        "-aspectpath",
                        javaCompile.classpath.asPath,
                        "-d",
                        project.buildDir.path + "/tmp/kotlin-classes/" + fullName,
                        "-classpath",
                        javaCompile.classpath.asPath,
                        "-bootclasspath",
                        android.bootClasspath.joinToString(File.pathSeparator)
                    )
                    log.info("ajc kotlinArgs: " + kotlinArgs.toTypedArray())
                    log.error("=====================================")
                    log.error("==LightAOP::Aspectj切片开始编织Class!==")
                    log.error("=====================================")
                    val handler = MessageHandler(true);
                    Main().run(javaArgs.toTypedArray(), handler);
                    Main().run(kotlinArgs.toTypedArray(), handler);
                    for (message in handler.getMessages(null, true)) {
                        when (message.kind) {
                            IMessage.ABORT,
                            IMessage.ERROR,
                            IMessage.FAIL ->
                                log.error(message.message, message.thrown)

                            IMessage.WARNING ->
                                log.warn(message.message, message.thrown)

                            IMessage.INFO ->
                                log.info(message.message, message.thrown)

                            IMessage.DEBUG ->
                                log.debug(message.message, message.thrown)
                        }
                    }

                }
            }
        }
    }
}