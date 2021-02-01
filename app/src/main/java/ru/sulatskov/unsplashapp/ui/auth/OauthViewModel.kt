package ru.sulatskov.unsplashapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.Event
import ru.sulatskov.unsplashapp.model.network.LoginApiInterface
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
import javax.inject.Inject

@HiltViewModel
class OauthViewModel @Inject constructor(
    private var loginApiInterface: LoginApiInterface,
    private var prefsService: PrefsService
) :
    BaseViewModel() {

    private val _token = MutableLiveData<Event<String>>()
    val token: LiveData<Event<String>> = _token

    fun login(code: String) {
        request(_token) { loginApiInterface.login(code).accessToken }
    }

    fun saveToken(token: String) {
        prefsService.accessToken = token
    }
}