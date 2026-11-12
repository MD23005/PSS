plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "dam.project.pss"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "dam.project.pss"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation("com.google.android.recaptcha:recaptcha:18.9.4")
    implementation("com.google.android.material:material:1.14.0")
    implementation("com.github.aritraroy:PinLockView:2.1.0") {
        exclude(group = "com.android.support")
    }

    // Room Database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Cloud Sync / Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // PostgreSQL Driver (Neon DB)
    implementation(libs.postgresql)
}