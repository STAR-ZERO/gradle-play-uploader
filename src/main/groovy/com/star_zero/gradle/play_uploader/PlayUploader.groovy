package com.star_zero.gradle.play_uploader

import org.gradle.api.Plugin
import org.gradle.api.Project

class PlayUploader implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def task = project.getTasks().create("playUpload", PlayUploadTask.class)
        task.extension = project.extensions.create("play", PlayUploaderExtension)

        project.android.applicationVariants.all { variant ->
            if ("release" == variant.buildType.name) {
                task.packageName = variant.applicationId
            }
        }
    }
}
