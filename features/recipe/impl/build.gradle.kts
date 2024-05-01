plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.aldemir.recipe"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":features:home:publ"))

    val koin_version = "3.2.0"
    implementation ("io.insert-koin:koin-androidx-compose:$koin_version")
    implementation ("io.insert-koin:koin-android:$koin_version")

    implementation("com.android.support:multidex:2.0.1")

    //Compose
    val composeVersion = "1.5.4"
    val lifecycleVersion = "2.7.0-rc02"
    implementation ("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.compose.material:material:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.compose.runtime:runtime-livedata:$composeVersion")
}