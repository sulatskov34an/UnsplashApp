package ru.sulatskov.unsplashapp.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.network.dto.Photo

interface ApiInterface {
    @GET("photos")
    suspend fun getListPhotos(
        @Query("page") page: Int?,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): List<Photo>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): Photo
}