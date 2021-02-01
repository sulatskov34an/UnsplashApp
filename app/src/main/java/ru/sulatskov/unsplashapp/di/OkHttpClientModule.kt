package ru.sulatskov.unsplashapp.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
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
    fun interceptor(prefsService: PrefsService) = Interceptor {
        try {
            val request = it.request()
            val newBuilder = request.newBuilder()
            if (prefsService.accessToken.isNotEmpty()) {
                newBuilder.header("Authorization", "Bearer ${prefsService.accessToken}")
            }
            val proceed = it.proceed(newBuilder.build())
            proceed
        } catch (e: Exception) {
            it.proceed(it.request())
        }
    }
}
