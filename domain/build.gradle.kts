plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
}

android {
    namespace = "br.com.aldemir.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

    }

}

dependencies {

    val koin_version = "3.2.0"
    implementation ("io.insert-koin:koin-androidx-compose:$koin_version")
    implementation ("io.insert-koin:koin-android:$koin_version")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}