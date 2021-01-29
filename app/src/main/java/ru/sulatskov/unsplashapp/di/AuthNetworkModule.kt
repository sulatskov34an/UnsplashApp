package ru.sulatskov.unsplashapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.network.LoginApiInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthNetworkModule {
    @Provides
    @Singleton
    fun provideAuthNetworkApi(): LoginApiInterface = Retrofit.Builder()
        .baseUrl(AppConst.AUTH_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient
                .Builder()
                .build())
        .build().create(LoginApiInterface::class.java)

}