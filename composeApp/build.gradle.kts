import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    sourceSets.named("androidMain").configure {
        kotlin.srcDirs("build/generated/ksp/metada/androidMain/kotlin")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)

            implementation(project(":data"))
            implementation(project(":common"))
            implementation(project(":domain"))
            implementation(project(":navigation"))
            implementation(project(":features:home:impl"))
            implementation(project(":features:recipe:impl"))
            implementation(project(":features:expense:impl"))
            implementation(project(":features:authentication:impl"))

            api(libs.logging)

            implementation(libs.android.core.ktx)
            implementation(libs.multidex)
            // Koin for Android
            implementation(libs.koin.android)
            //Compose
            implementation(libs.compose.lifecycle.viewmodel)
            implementation(libs.compoose.constraintlayout)
            // Paging
            implementation(libs.paging.compose)
            implementation(libs.compose.navigation)
        }
        commonMain.dependencies {
            implementation(project(":common"))
            implementation(project(":domain"))
            implementation(project(":navigation"))
            implementation(project(":features:recipe:impl"))
            implementation(project(":features:expense:impl"))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.compose.lifecycle.viewmodel)
        }
        iosMain.dependencies {
            implementation(compose.ui)
        }
        iosSimulatorArm64Main.dependencies {
            implementation(compose.ui)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
    }
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

    buildFeatures {
        compose = true
        viewBinding = true
    }
    namespace = "br.com.aldemir.myaccounts"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.tooling)
}