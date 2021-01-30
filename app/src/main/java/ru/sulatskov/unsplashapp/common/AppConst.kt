package ru.sulatskov.unsplashapp.common

object AppConst {
    const val BASE_URL = "https://api.unsplash.com/"
    const val AUTH_URL = "https://unsplash.com/"
    const val ACCESS_KEY = "Uqh1WK4CMFpDoW_SdPXNFLlqUB1nrHV1hDgdzcF-PDw"

    const val unsplashAuthCallback: String = "unsplash-auth-callback"
    const val redirectUri = "resplash://${unsplashAuthCallback}"
    const val clientSecret = "RH-KS9CmnK_Ch_eWbE8GwtCHyA7mpmZojOGJRqthD_0"
    const val grantType = "authorization_code"

    const val loginUrl = "https://unsplash.com/oauth/authorize" +
            "?client_id=${ACCESS_KEY}" +
            "&redirect_uri=resplash%3A%2F%2F${unsplashAuthCallback}" +
            "&response_type=code" +
            "&scope=public+read_user+write_user+read_photos+write_photos" +
            "+write_likes+write_followers+read_collections+write_collections"

    const val STABLE_PACKAGE = "com.android.chrome"
    const val BETA_PACKAGE = "com.chrome.beta"
    const val DEV_PACKAGE = "com.chrome.dev"
    const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
}
