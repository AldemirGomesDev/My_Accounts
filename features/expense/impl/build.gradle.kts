plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose)
}

android {
    namespace = "br.com.aldemir.expense"
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":features:home:publ"))

    implementation (libs.bundles.koin.all)

    implementation(libs.multidex)

    //Compose
    implementation (libs.bundles.compose.all)
    implementation (libs.compose.lifecycle.viewmodel)
    implementation (libs.compoose.constraintlayout)
}