package ru.sulatskov.unsplashapp.model.network.dto

import com.google.gson.annotations.SerializedName

class Photo(
    val id: String? = "",
    val urls: Urls? = Urls(),
    val description: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    val likes: Int? = 0,
    val location: Location? = Location(),
    val user: User? = User()
)

class Location(val title: String = "")

class User(
    val username: String = "",
    val name: String = "",
    @SerializedName("profile_image")
    val profileImage: ProfileImage = ProfileImage()
)

class ProfileImage(val medium: String = "")

class Urls(val regular: String? = "")

class AccessToken(
    @SerializedName("access_token")
    val accessToken: String = ""
)
