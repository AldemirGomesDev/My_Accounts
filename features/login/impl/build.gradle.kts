plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose)
}

android {
    namespace = "br.com.aldemir.login"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(libs.android.core.ktx)
    implementation(libs.appCompat)
    implementation(libs.androidMaterial)
    implementation(libs.bundles.koin.all)
    implementation(libs.multidex)
    //Compose
    implementation(libs.bundles.compose.all)
    implementation(libs.compose.lifecycle.viewmodel)

    implementation(libs.biometric)
    // tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}