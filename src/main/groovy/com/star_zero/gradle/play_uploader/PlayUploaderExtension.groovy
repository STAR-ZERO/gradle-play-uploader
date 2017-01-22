package com.star_zero.gradle.play_uploader

import org.gradle.api.GradleException

class PlayUploaderExtension {
    String credentialJson
    String apk
    String track
    double userFraction
    String mapping

    def validate() {
        if (credentialJson == null) {
            throw new GradleException("'json' is not specified.")
        }
        if (apk == null) {
            throw new GradleException("'apk' is not specified.")
        }
        if (track == null) {
            throw new GradleException("'track' is not specified.")
        }
        if (!(track in ['alpha', 'beta', 'rollout', 'production'])) {
            throw new GradleException("'track' is invalid. Specify 'alpha', 'beta', 'rollout' or 'production'")
        }
        if (track == 'rollout' && !(userFraction > 0 && userFraction < 1)) {
            // 0.0 - 1.0
            throw new GradleException("'userFraction' must be greater than 0.0 and less than 1.0")
        }
    }
}
