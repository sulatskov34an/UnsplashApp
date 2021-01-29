package ru.sulatskov.unsplashapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.di.AuthNetworkModule
import ru.sulatskov.unsplashapp.network.LoginApiInterface
import ru.sulatskov.unsplashapp.network.MainApiInterface
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private var loginApiInterface: LoginApiInterface
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    suspend fun login(code: String) {
        val result = loginApiInterface.login(code)
        _token.value = result.accessToken
    }

}