object Version {
    const val COROUTINE = "1.6.1"

    const val CORE = "1.7.0"
    const val APPCOMPAT = "1.5.1"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val MATERIAL = "1.7.0"
    const val VIEWMODEL = "2.5.1"
    const val ACTIVITY_KTX = "1.5.1"
    const val FRAGMENT_KTX = "1.5.4"
    const val PAGING = "3.1.1"
    const val ANDROID_JUNIT = "1.1.4"
    const val ESPRESSO = "3.5.0"

    const val JUNIT = "4.13.2"

    const val HILT = "2.42"
    const val ROOM = "2.5.0"

    const val RETROFIT = "2.9.0"
    const val MOSHI = "1.9.3"
    const val OKHTTP = "5.0.0-alpha.7"

    const val GLIDE = "4.13.2"
    const val GLIDE_COMPILER = "4.12.0"

    const val LOTTIE = "5.2.0"
}

object Library {
    object Kotlin {
        const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINE}"
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINE}"
    }

    object AndroidX {
        const val CORE = "androidx.core:core-ktx:${Version.CORE}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
        const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.VIEWMODEL}"
        const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Version.ACTIVITY_KTX}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Version.FRAGMENT_KTX}"
        const val PAGING = "androidx.paging:paging-runtime-ktx:${Version.PAGING}"
        const val PAGING_COMMON = "androidx.paging:paging-common-ktx:${Version.PAGING}"
        const val ANDROID_JUNIT = "androidx.test.ext:junit:${Version.ANDROID_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    }

    object Junit {
        const val JUNIT = "junit:junit:${Version.JUNIT}"
    }

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
        const val CORE = "com.google.dagger:hilt-core:${Version.HILT}"
        const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
        const val COMPILER = "com.google.dagger:hilt-compiler:${Version.HILT}"
    }

    object Room {
        const val ROOM = "androidx.room:room-runtime:${Version.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Version.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Version.ROOM}"
        const val ROOM_PAGING = "androidx.room:room-paging:${Version.ROOM}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Version.RETROFIT}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Version.MOSHI}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Version.OKHTTP}"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Version.OKHTTP}"
    }

    object Glide {
        const val GLIDE = "com.github.bumptech.glide:glide:${Version.GLIDE}"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Version.GLIDE_COMPILER}"
    }

    object Lottie {
        const val LOTTIE = "com.airbnb.android:lottie:${Version.LOTTIE}"
    }
}