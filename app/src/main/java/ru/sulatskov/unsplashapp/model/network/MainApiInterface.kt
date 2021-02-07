package ru.sulatskov.unsplashapp.model.network

import retrofit2.http.*
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.model.network.dto.*

interface MainApiInterface {
    @GET("photos")
    suspend fun getListPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = 50,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): List<Photo>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): Photo

    @GET("me")
    suspend fun getUser(): UserProfile

    @POST("photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String?,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): Any

    @DELETE("photos/{id}/like")
    suspend fun unlikePhoto(
        @Path("id") id: String?,
        @Query("client_id") clientId: String = AppConst.ACCESS_KEY
    ): Any
}