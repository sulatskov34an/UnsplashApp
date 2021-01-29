package ru.sulatskov.unsplashapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.Event
import ru.sulatskov.unsplashapp.di.AuthNetworkModule
import ru.sulatskov.unsplashapp.network.LoginApiInterface
import ru.sulatskov.unsplashapp.network.MainApiInterface
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private var loginApiInterface: LoginApiInterface) :
    BaseViewModel() {

    private val _token = MutableLiveData<Event<String>>()
    val token: LiveData<Event<String>> = _token

    fun login(code: String) {
        request(_token) { loginApiInterface.login(code).accessToken }
    }
}