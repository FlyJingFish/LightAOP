/*
 * Copyright 2018 firefly1126, Inc.

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.gradle_plugin_android_aspectjx
 */
// This plugin is based on https://github.com/JakeWharton/hugo
package com.flyjingfish.light_aop_plugin

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.flyjingfish.light_aop_plugin.internal.TimeTrace
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * aspectj plugin,
 * @author simon
 * @version 1.0.0
 * @since 2016-04-20
 */
class AJXPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.repositories {
            mavenLocal()
        }

        project.dependencies {
            if (project.gradle.gradleVersion > "4.0") {
                project.logger.debug("gradlew version > 4.0")
                implementation 'org.aspectj:aspectjrt:1.9.5'
            } else {
                project.logger.debug("gradlew version < 4.0")
                compile 'org.aspectj:aspectjrt:1.9.5'
            }
        }

        project.extensions.create("aspectjx", AJXExtension)

        if (project.plugins.hasPlugin(AppPlugin)) {
            //build time trace
            project.gradle.addListener(new TimeTrace())
            //register AspectTransform
            def androidComponents = project.extensions.getByType(AndroidComponentsExtension.class)
            androidComponents.onVariants { variant ->
                def task = project.tasks.register("${variant.name}AssembleModuleAspectJTask", AJXTransform)
                variant.artifacts
                        .forScope(ScopedArtifacts.Scope.ALL)
                        .use(task)
                        .toTransform(
                                ScopedArtifact.CLASSES,
                                AssembleModuleRouteTask::allJars,
                                AssembleModuleRouteTask::allDirectories,
                                AssembleModuleRouteTask::output
                        )
            }
        }


    }
}
