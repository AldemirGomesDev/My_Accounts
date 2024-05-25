plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "br.com.aldemir.publ"
    compileSdk = libs.versions.compileSdk.get().toInt()

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
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