import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
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
        androidMain.dependencies {  }
        commonMain.dependencies {
            implementation(project(":common"))
            implementation(project(":domain"))

            implementation(libs.bundles.koin.all)

            //DATA STORE PREFERENCES
            implementation(libs.datastore.preferences)

            implementation(libs.retrofit)
            implementation(libs.gsonConverter)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.logger)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization.json)
        }
    }
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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    //room
    implementation(libs.bundles.room.all)
    ksp (libs.room.compiler)
}