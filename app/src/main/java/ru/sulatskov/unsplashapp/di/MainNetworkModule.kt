package ru.sulatskov.unsplashapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.model.network.MainApiInterface
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
@InstallIn(SingletonComponent::class)
class MainNetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MainApiInterface =
        retrofit.create(MainApiInterface::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInterface(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConst.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}