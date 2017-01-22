package com.star_zero.gradle.play_uploader

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class PlayUploaderTest {

    @Test
    void generateTask() throws Exception {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.android.application'
        project.pluginManager.apply 'com.star-zero.gradle-play-uploader'

        assertTrue(project.tasks.playUpload instanceof PlayUploadTask)
    }

}