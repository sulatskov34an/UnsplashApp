package ru.sulatskov.unsplashapp.ui.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.network.LoginApiInterface
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private var loginApiInterface: LoginApiInterface) :
    BaseViewModel() {

    fun hasToken() = false
}