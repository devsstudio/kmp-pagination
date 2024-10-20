# Form and validations

Kotlin Multiplatform Library

### Publish to MavenCentral

1) Registering a Sonatype account as described here: 
   https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
2) Add developer id, name, email and the project url to
   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
3) Add the secrets to `local.properties`:

```
signing.keyId=...
signing.password=...
signing.secretKeyRingFile=...
ossrhUsername=...
ossrhPassword=...
```
* Generate the secretKeyRingFile
* https://dev.to/vladleesi/how-to-set-up-publication-signature-with-the-gradle-plugin-hfa

4) Run `./gradlew :pagination:publishAllPublicationsToSonatypeRepository`

### Build platform artifacts

#### Android aar

- Run `./gradlew :pagination:assembleRelease`
- Output: `/pagination/build/outputs/aar/pagination-release.aar`

#### JVM jar

- Run `./gradlew :pagination:jvmJar`
- Output: `/pagination/build/libs/pagination-jvm-1.0.jar`

#### iOS Framework

- Run `./gradlew :pagination:linkReleaseFrameworkIosArm64`
- Output: `/pagination/build/bin/iosArm64/releaseFramework/pagination.framework`

#### JS file

- Run `./gradlew :pagination:jsBrowserProductionWebpack`
- Output: `/pagination/build/dist/js/productionExecutable/pagination.js`

#### macOS Framework

- Run `./gradlew :pagination:linkReleaseFrameworkMacosArm64`
- Output: `/pagination/build/bin/macosArm64/releaseFramework/pagination.framework`

#### Linux static library

- Run `./gradlew :pagination:linkReleaseStaticLinuxX64`
- Output: `/pagination/build/bin/linuxX64/releaseStatic/libpagination.a`

#### Windows static library

- Run `./gradlew :pagination:linkReleaseStaticMingwX64`
- Output: `/pagination/build/bin/mingwX64/releaseStatic/libpagination.a`

#### Wasm binary file

- Run `./gradlew :pagination:wasmJsBrowserDistribution`
- Output: `/pagination/build/dist/wasmJs/productionExecutable/pagination-wasm-js.wasm`
