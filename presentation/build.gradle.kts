import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = AppConfig.NAMESPACE
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Library.AndroidX.MATERIAL)
    implementation(Library.AndroidX.VIEWMODEL)
    implementation(Library.AndroidX.ACTIVITY_KTX)
    implementation(Library.AndroidX.FRAGMENT_KTX)
    androidTestImplementation(Library.AndroidX.ANDROID_JUNIT)
    androidTestImplementation(Library.AndroidX.ESPRESSO)

    implementation(Library.Junit.JUNIT)

    implementation(Library.Hilt.ANDROID)
    kapt(Library.Hilt.ANDROID_COMPILER)

    implementation(Library.Glide.GLIDE)
    kapt(Library.Glide.GLIDE_COMPILER)

    implementation(Library.Lottie.LOTTIE)
}