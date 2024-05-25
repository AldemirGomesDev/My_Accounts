// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.ksp) apply false
}


buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
//    dependencies {
//        classpath ("com.android.tools.build:gradle:8.4.0")
//        classpath ("com.google.gms:google-services:4.4.1")
//        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
//    }
}
