package com.flyjingfish.light_aop_plugin

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class LightAopJoinPointPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isApp = project.plugins.hasPlugin(AppPlugin::class.java)
        val logger = project.logger;
        if (!isApp) {
            logger.error("Plugin ['light.aop'] can only be used under the application, not under the module library invalid!")
            return
        }
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            val task = project.tasks.register("${variant.name}AssembleLightAopTask", AssembleLightAopTask::class.java)
            variant.artifacts
                .forScope(ScopedArtifacts.Scope.ALL)
                .use(task)
                .toTransform(
                    ScopedArtifact.CLASSES,
                    AssembleLightAopTask::allJars,
                    AssembleLightAopTask::allDirectories,
                    AssembleLightAopTask::output
                )
        }
    }
}