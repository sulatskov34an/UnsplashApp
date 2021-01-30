package ru.sulatskov.unsplashapp.model.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsService(context: Context) {
    private val prefs = context.getSharedPreferences("unsplash", Context.MODE_PRIVATE)

    companion object {
        const val TOKEN_KEY = "token"
    }

    var accessToken: String?
        get() = prefs.getString(TOKEN_KEY, "")
        set(value) {
            prefs.edit {
                putString(TOKEN_KEY, value)
            }
        }
}