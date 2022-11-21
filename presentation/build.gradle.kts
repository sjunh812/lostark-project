plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = AppConfig.NAMESPACE

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
    implementation(":domain")

    implementation(Library.AndroidX.CORE)
    implementation(Library.AndroidX.APPCOMPAT)
    implementation(Library.AndroidX.CONSTRAINT_LAYOUT)
    androidTestImplementation(Library.AndroidX.ANDROID_JUNIT)
    androidTestImplementation(Library.AndroidX.ESPRESSO)

    implementation(Library.Junit.JUNIT)

    implementation(Library.Google.MATERIAL)

    implementation(Library.Hilt.ANDROID)
    kapt(Library.Hilt.ANDROID_COMPILER)
}