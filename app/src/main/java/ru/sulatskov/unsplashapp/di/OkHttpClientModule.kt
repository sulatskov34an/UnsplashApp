package ru.sulatskov.unsplashapp.di

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
    fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    @Singleton
    fun interceptor(prefsService: PrefsService) = Interceptor { chain: Interceptor.Chain ->
        val newBuilder = chain.request().newBuilder()
        if (prefsService.accessToken.isNotEmpty()) {
            newBuilder.header("Authorization", "Bearer ${prefsService.accessToken}")
        }
        val proceed = chain.proceed(newBuilder.build())

// refreshToken 
//        if (!proceed.isSuccessful && proceed.code == 401) {
//            val refreshResponse = api.refreshToken(prefsService.refreshToken).execute()
//            if (refreshResponse.isSuccessful) {
//                refreshResponse.body().apply {
//                    prefsService.accessToken = result.accessToken.token
//                    prefsService.refreshToken = result.refreshToken.token
//                }
//                if (prefsService.accessToken.isNotEmpty()) {
//                    newBuilder.header("Authorization", "Bearer ${prefsService.accessToken}")
//                }
//                proceed = chain.proceed(newBuilder.build())
//            }
//        }
        proceed
    }
}
