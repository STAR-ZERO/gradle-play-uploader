DEPRECATED
===

This repository is no longer maintained.

gradle-play-uploader
===

Gradle Plugin to upload APK and Proguard mapping file to Google Play Developer Console.

## Install

Add dependencis to project build.gradle.

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        // ...
        
        // Add
        classpath 'com.star_zero:gradle-play-uploader:1.0.0'
    }
}
```

Add apply plugin to module build.gradle. (e.g. `app/build.gradle`)

```groovy
apply plugin: 'com.star-zero.gradle-play-uploader'
```

The plugin creates `playUpload` Task.

## Usage

### Example

Add configure to module build.gradle.

```groovy
play {
    credentialJson = 'credential.json'
    apk = 'app/build/outputs/apk/app-release.apk'
    track = 'beta'
    mapping = 'app/build/outputs/mapping/release/mapping.txt'
}
```

### Run task

```
$ ./gradlew playupload
```

## Configuration

### credentialJson

Required.

Set Google Developers Service Account credential JSON file path.

See [this(fastlane)](https://github.com/fastlane/fastlane/tree/master/supply#setup).

**TODO:** Write by myself later.

### apk

Required.

APK file path

### track

Required.

`production` or `rollout` or `beta` or `alpha`

Details see [this](https://developers.google.com/android-publisher/tracks).

### mapping

Optional.

If you need upload proguard mapping file, set mapping.txt file path.

### userFraction

If `track` is `rollout`, Required.

Set the percentage of users to publish. (e.g. 0.1 = 10%)

Only greater than 0 and less than 1.

Details see [this](https://developers.google.com/android-publisher/tracks).

## License

MIT

See [LICENSE](LICENSE)
