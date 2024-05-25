plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
}

android {
    namespace = "br.com.aldemir.publ"
    compileSdk = libs.versions.compileSdk.get().toInt()

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.multidex)
}