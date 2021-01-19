package ru.sulatskov.unsplashapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        baseInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(baseInterceptor)
        .build()

    @Provides
    @Singleton
    fun loggingInterceptor() = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun interceptor() = Interceptor.invoke { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()

        return@invoke chain.proceed(request)
    }
}