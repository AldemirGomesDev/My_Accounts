plugins {
    id ("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.aldemir.home"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
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
    val compose_version = "1.4.3"
    val lifecycle_version = "2.6.0-alpha01"
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.3")

    implementation("io.github.bytebeats:compose-charts:0.1.2")
}
