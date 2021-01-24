package ru.sulatskov.unsplashapp.common

import ru.sulatskov.unsplashapp.ui.profile.ProfileFragment

object AppConst {
    const val BASE_URL = "https://api.unsplash.com/"
    const val AUTH_URL = "https://unsplash.com/"
    const val ACCESS_KEY = "Uqh1WK4CMFpDoW_SdPXNFLlqUB1nrHV1hDgdzcF-PDw"

    const val redirectUri = "resplash://${ProfileFragment.unsplashAuthCallback}"
    const val clientSecret = "RH-KS9CmnK_Ch_eWbE8GwtCHyA7mpmZojOGJRqthD_0"
    const val grantType = "authorization_code"
}
