package com.star_zero.gradle.play_uploader

import org.gradle.api.GradleException
import org.junit.Test

import static org.junit.Assert.fail

class PlayUploaderExtensionTest {

    @Test
    void validateNonError() throws Exception {
        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'beta'
        extension.mapping = 'mapping.txt'
        extension.validate()

        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'rollout'
        extension.userFraction = 0.5
        extension.validate()
    }

    @Test(expected = GradleException)
    void validateEmptyJson() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.apk = 'app.apk'
        extension.track = 'beta'

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateEmptyApk() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.track = 'beta'

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateEmptyTrack() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateInvalidTrack() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'invalid'

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateRolloutEmptyUserFraction() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'rollout'

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateUserFractionNotGreaterThanZero() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'rollout'
        extension.userFraction = 0

        extension.validate()

        fail()
    }

    @Test(expected = GradleException)
    void validateUserFractionNotLessThanOne() throws Exception {

        PlayUploaderExtension extension = new PlayUploaderExtension()
        extension.credentialJson = 'credential.json'
        extension.apk = 'app.apk'
        extension.track = 'rollout'
        extension.userFraction = 1

        extension.validate()

        fail()
    }

}