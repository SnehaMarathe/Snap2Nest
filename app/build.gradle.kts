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
        versionCode = 1
        versionName = "1.0.0"
    }

    // CI/Play signing support.
    // - In GitHub Actions we decode a base64 keystore into a file and set env vars.
    // - If env vars are missing, we fall back to debug signing so local builds still work.
    val keystorePath = System.getenv("SIGNING_KEYSTORE_PATH")
    val keystoreFile = keystorePath?.let { file(it) } ?: file("upload-keystore.jks")
    val storePasswordEnv = System.getenv("SIGNING_STORE_PASSWORD")
    val keyAliasEnv = System.getenv("SIGNING_KEY_ALIAS")
    val keyPasswordEnv = System.getenv("SIGNING_KEY_PASSWORD")
    val hasReleaseSigning =
        storePasswordEnv != null && keyAliasEnv != null && keyPasswordEnv != null && keystoreFile.exists()

    signingConfigs {
        create("release") {
            if (hasReleaseSigning) {
                storeFile = keystoreFile
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
            signingConfig = if (hasReleaseSigning) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
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
