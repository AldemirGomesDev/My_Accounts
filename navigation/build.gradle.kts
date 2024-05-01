plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    namespace = "br.com.aldemir.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":features:home:impl"))
    implementation(project(":features:recipe:impl"))
    implementation(project(":features:expense:impl"))

    //Compose
    val composeVersion = "1.4.3"
    val lifecycleVersion = "2.7.0"
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.material:material:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")
    val material3Version = "1.1.2"
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation("androidx.navigation:navigation-compose:2.7.6")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}