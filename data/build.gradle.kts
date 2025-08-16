plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "br.com.aldemir.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    //room
    implementation(libs.bundles.room.all)
    ksp (libs.room.compiler)

    implementation(libs.bundles.koin.all)

    //DATA STORE PREFERENCES
    implementation(libs.datastore.preferences)

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
}