plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "br.com.aldemir.myaccounts"
        buildToolsVersion = libs.versions.buildTools.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
    namespace = "br.com.aldemir.myaccounts"
}

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":navigation"))
    implementation(project(":features:home:impl"))
    implementation(project(":features:recipe:impl"))
    implementation(project(":features:expense:impl"))
    implementation(project(":features:authentication:impl"))

    implementation(libs.android.core.ktx)
    implementation(libs.multidex)
    // Koin for Android
    implementation(libs.bundles.koin.all)
    //Compose
    implementation(libs.bundles.compose.all)
    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.compoose.constraintlayout)
    // Paging
    implementation(libs.paging.compose)
    implementation(libs.compose.navigation)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.tooling)
}