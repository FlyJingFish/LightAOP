package com.flyjingfish.light_aop_plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.compile.JavaCompile

/**
 * @使用ajc编译java代码 ， 同 时 织 入 切 片 代 码
 * 使用 AspectJ 的编译器（ajc，一个java编译器的扩展）
 * 对所有受 aspect 影响的类进行织入。
 * 在 gradle 的编译 task 中增加额外配置，使之能正确编译运行。
 */
class LightAopPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.dependencies {
            implementation 'org.aspectj:aspectjrt:1.9.8'
        }

        log.error "====================================="
        log.error "==LightAOP::Aspectj切片开始编织Class!=="
        log.error "====================================="

        variants.all { variant ->
            variant.outputs.all { output ->
                JavaCompile javaCompile = null
                if (variant.hasProperty('javaCompileProvider')) {
                    //gradle 4.10.1 +
                    TaskProvider<JavaCompile> provider = variant.javaCompileProvider
                    javaCompile = provider.get()
                } else {
                    javaCompile = variant.hasProperty('javaCompiler') ? variant.javaCompiler : variant.javaCompile
                }
                def fullName = ""
                output.name.tokenize('-').eachWithIndex { token, index ->
                    fullName = fullName + (index == 0 ? token : token.capitalize())
                }
                javaCompile.doLast {
                    String[] javaArgs = ["-showWeaveInfo",
                                         "-1.8",
                                         "-inpath", javaCompile.destinationDirectory.asFile.getOrNull().toString(),
                                         "-aspectpath", javaCompile.classpath.asPath,
                                         "-d", javaCompile.destinationDirectory.asFile.getOrNull().toString(),
                                         "-classpath", javaCompile.classpath.asPath,
                                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                    println "ajc javaArgs: " + Arrays.toString(javaArgs)
                    String[] kotlinArgs = ["-showWeaveInfo",
                                           "-1.8",
                                           "-inpath", project.buildDir.path + "/tmp/kotlin-classes/" + fullName,
                                           "-aspectpath", javaCompile.classpath.asPath,
                                           "-d", project.buildDir.path + "/tmp/kotlin-classes/" + fullName,
                                           "-classpath", javaCompile.classpath.asPath,
                                           "-bootclasspath", project.android.bootClasspath.join(
                            File.pathSeparator)]
                    println "ajc kotlinArgs: " + Arrays.toString(kotlinArgs)
                    MessageHandler handler = new MessageHandler(true);
                    new Main().run(javaArgs, handler);
                    for (IMessage message : handler.getMessages(null, true)) {
                        switch (message.getKind()) {
                            case IMessage.ABORT:
                            case IMessage.ERROR:
                            case IMessage.FAIL:
                                log.error message.message, message.thrown
                                break;
                            case IMessage.WARNING:
                                log.warn message.message, message.thrown
                                break;
                            case IMessage.INFO:
                                log.info message.message, message.thrown
                                break;
                            case IMessage.DEBUG:
                                log.debug message.message, message.thrown
                                break;
                        }
                    }

                    MessageHandler handler2 = new MessageHandler(true);
                    new Main().run(kotlinArgs, handler2);
                    for (IMessage message : handler2.getMessages(null, true)) {
                        switch (message.getKind()) {
                            case IMessage.ABORT:
                            case IMessage.ERROR:
                            case IMessage.FAIL:
                                log.error message.message, message.thrown
                                break;
                            case IMessage.WARNING:
                                log.warn message.message, message.thrown
                                break;
                            case IMessage.INFO:
                                log.info message.message, message.thrown
                                break;
                            case IMessage.DEBUG:
                                log.debug message.message, message.thrown
                                break;
                        }
                    }
                }
            }
        }
    }
}
