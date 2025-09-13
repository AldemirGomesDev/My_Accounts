import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
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
        commonMain.dependencies {
            implementation(project(":data"))
            implementation(project(":common"))
            implementation(project(":domain"))
            implementation(project(":features:home:publ"))

            implementation(libs.compose.material3)
            implementation(libs.bundles.koin.all)

            implementation(libs.multidex)

            //Compose
            implementation(libs.bundles.compose.all)
            implementation(libs.compose.lifecycle.viewmodel)
        }
    }
}

android {
    namespace = "br.com.aldemir.recipe"
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }
}
