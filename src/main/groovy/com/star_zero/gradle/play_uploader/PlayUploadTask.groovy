package com.star_zero.gradle.play_uploader

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.AbstractInputStreamContent
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisher.Edits
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apks.Upload
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Commit
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Insert
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Tracks.Update
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import com.google.api.services.androidpublisher.model.Apk
import com.google.api.services.androidpublisher.model.AppEdit
import com.google.api.services.androidpublisher.model.Track
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PlayUploadTask extends DefaultTask {

    private static final APP_NAME = 'gradle-play-uploader'

    PlayUploaderExtension extension

    String packageName

    @TaskAction
    playUpload() {

        extension.validate()

        AndroidPublisher service = getService()
        Edits edits = service.edits()
        Insert editRequest = edits.insert(packageName, null)
        AppEdit appEdit = editRequest.execute()

        String editId = appEdit.getId()

        AbstractInputStreamContent apkFile = new FileContent("application/vnd.android.package-archive", new File(extension.apk))
        Upload uploadRequest = edits.apks().upload(packageName, editId, apkFile)
        Apk apk = uploadRequest.execute()

        Track track = new Track()
        track.setVersionCodes([apk.getVersionCode()])
        if (extension.track == 'rollout') {
            track.setUserFraction(extension.userFraction)
        }
        Update updateTrackRequest = edits.tracks().update(packageName, editId, extension.track, track)
        updateTrackRequest.execute()

        if (extension.mapping != null) {
            AbstractInputStreamContent mappingFile = new FileContent("application/octet-stream", new File(extension.mapping))
            edits.deobfuscationfiles().upload(packageName, editId, apk.getVersionCode(), "proguard", mappingFile).execute()
        }

        Commit commitRequest = edits.commit(packageName, editId)
        commitRequest.execute()
    }

    private AndroidPublisher getService() {
        GoogleCredential credential = GoogleCredential.fromStream(new File(extension.credentialJson).newInputStream())
                .createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))

        return new AndroidPublisher.Builder(GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.defaultInstance, credential)
                .setApplicationName(APP_NAME).build()
    }
}

