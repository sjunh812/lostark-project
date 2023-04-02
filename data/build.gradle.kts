plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Library.Kotlin.COROUTINE_CORE)

    implementation(Library.Hilt.CORE)
    kapt(Library.Hilt.COMPILER)

    implementation(Library.Room.ROOM)
    implementation(Library.Room.ROOM_KTX)
    kapt(Library.Room.ROOM_COMPILER)

    implementation(Library.Network.RETROFIT)
    implementation(Library.Network.CONVERTER_MOSHI)
    implementation(Library.Network.MOSHI)
    implementation(Library.Network.OKHTTP)
    implementation(Library.Network.LOGGING_INTERCEPTOR)
}