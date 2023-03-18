package org.sjhstudio.lostark.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.sjhstudio.lostark.data.api.ApiKey.LOST_ARK_API_KEY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://developer-lostark.game.onstove.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
//        val trustAllCerts = arrayOf<TrustManager>(@Suppress("CustomX509TrustManager")
//        object : X509TrustManager {
//            @Suppress("TrustAllX509TrustManager")
//            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
//
//            @Suppress("TrustAllX509TrustManager")
//            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
//
//            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//        })
//        val sslContext = SSLContext.getInstance("SSL")
//            .apply { init(null, trustAllCerts, SecureRandom()) }
//        val sslSocketFactory = sslContext.socketFactory
//
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(HeaderInterceptor(LOST_ARK_API_KEY))
//            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
//            .hostnameVerifier { hostname, session -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}

internal class HeaderInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer $token"
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(newRequest)
    }
}