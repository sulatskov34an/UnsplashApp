package ru.sulatskov.unsplashapp.network

import retrofit2.http.POST
import retrofit2.http.Query
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.network.dto.AccessToken

interface LoginApiInterface {
    @POST("oauth/token")
    suspend fun login(
        @Query("code") code: String,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY,
        @Query("client_secret") clientSecret: String = AppConst.clientSecret,
        @Query("redirect_uri") redirectUri: String = AppConst.redirectUri,
        @Query("grant_type") grantType: String = AppConst.grantType,
    ): AccessToken
}