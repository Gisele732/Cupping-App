plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cuppingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cuppingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.runtime)
    implementation(libs.firebase.inappmessaging)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test:core:1.4.0")

    // JUnit for unit testing
    testImplementation("junit:junit:4.13.2")  // Use 4.13.2 or any latest stable version

    // Robolectric dependency
    testImplementation("org.robolectric:robolectric:4.7.3")

    // Mockito for mocking (if required)
    testImplementation("org.mockito:mockito-core:3.11.2")

    // Espresso for UI testing (if required for instrumentation tests)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")


}