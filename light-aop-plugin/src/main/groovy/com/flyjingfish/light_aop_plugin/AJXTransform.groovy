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
package com.flyjingfish.light_aop_plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.flyjingfish.light_aop_plugin.internal.cache.VariantCache
import com.flyjingfish.light_aop_plugin.internal.procedure.*
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * class description here
 * @author simon
 * @version 1.0.0
 * @since 2018-03-12
 */
class AJXTransform extends DefaultTask {

    AJXProcedure ajxProcedure

    @InputFile
    abstract ListProperty<RegularFile> allJars

    @InputFile
    abstract ListProperty<Directory> allDirectories

    @OutputFile
    abstract RegularFileProperty output


    @TaskAction
    void transform(TransformInvocation transformInvocation) {
        ajxProcedure = new AJXProcedure(getProject())

        Project project = ajxProcedure.project

        String transformTaskVariantName = transformInvocation.context.getVariantName()
        VariantCache variantCache = new VariantCache(ajxProcedure.project, ajxProcedure.ajxCache, transformTaskVariantName)
        
        ajxProcedure.with(new CheckAspectJXEnableProcedure(project, variantCache, transformInvocation))

        if (transformInvocation.incremental) {
            //incremental build
            ajxProcedure.with(new UpdateAspectFilesProcedure(project, variantCache, transformInvocation))
            ajxProcedure.with(new UpdateInputFilesProcedure(project, variantCache, transformInvocation))
            ajxProcedure.with(new UpdateAspectOutputProcedure(project, variantCache, transformInvocation))
        } else {
            //delete output and cache before full build
            transformInvocation.outputProvider.deleteAll()
            variantCache.reset()

            ajxProcedure.with(new CacheAspectFilesProcedure(project, variantCache, transformInvocation))
            ajxProcedure.with(new CacheInputFilesProcedure(project, variantCache, transformInvocation))
            ajxProcedure.with(new DoAspectWorkProcedure(project, variantCache, transformInvocation))
        }

        ajxProcedure.with(new OnFinishedProcedure(project, variantCache, transformInvocation))

        ajxProcedure.doWorkContinuously()
    }
}
