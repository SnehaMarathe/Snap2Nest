plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    // Final app namespace for Play Store. Keep this stable after publishing.
    namespace = "com.snehamarathe.snap2nest"
    compileSdk = 34

    defaultConfig {
        // Final applicationId (package name) for Play Store. Keep this stable after publishing.
        applicationId = "com.snehamarathe.snap2nest"
        minSdk = 26
        targetSdk = 34

        // IMPORTANT: must be incremented for every Play upload
        versionCode = 2
        versionName = "1.0.0"
    }

    /**
     * Release signing for CI / Play Store
     *
     * GitHub Actions provides:
     *  - KEYSTORE_PATH        -> path to decoded upload-keystore.jks
     *  - KEYSTORE_PASSWORD
     *  - KEY_ALIAS
     *  - KEY_PASSWORD
     *
     * For Play Console, release builds MUST NOT be debug-signed.
     * If signing env vars are missing, the build should fail rather than falling back to debug.
     */
    val keystorePath = System.getenv("KEYSTORE_PATH")
    val storePasswordEnv = System.getenv("KEYSTORE_PASSWORD")
    val keyAliasEnv = System.getenv("KEY_ALIAS")
    val keyPasswordEnv = System.getenv("KEY_PASSWORD")

    val hasReleaseSigning =
        !keystorePath.isNullOrBlank() &&
        !storePasswordEnv.isNullOrBlank() &&
        !keyAliasEnv.isNullOrBlank() &&
        !keyPasswordEnv.isNullOrBlank()

    signingConfigs {
        create("release") {
            if (hasReleaseSigning) {
                storeFile = file(keystorePath!!)
                storePassword = storePasswordEnv
                keyAlias = keyAliasEnv
                keyPassword = keyPasswordEnv
            }
        }
    }

    buildTypes {
        getByName("release") {
            // Keep simple for first Play launch. You can enable minify later.
            isMinifyEnabled = false

            // IMPORTANT: Always use release signing config for release builds
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            // keep default debug signing
        }
    }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    implementation("com.google.android.material:material:1.12.0")

    // uCrop (JitPack)
    implementation("com.github.yalantis:ucrop:2.2.11")

    // CameraX
    val camerax = "1.3.4"
    implementation("androidx.camera:camera-core:$camerax")
    implementation("androidx.camera:camera-camera2:$camerax")
    implementation("androidx.camera:camera-lifecycle:$camerax")
    implementation("androidx.camera:camera-view:$camerax")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}

kotlin { jvmToolchain(17) }
