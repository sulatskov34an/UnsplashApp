package ru.sulatskov.unsplashapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.Event
import ru.sulatskov.unsplashapp.model.network.MainApiInterface
import ru.sulatskov.unsplashapp.model.network.dto.UserProfile
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private var prefsService: PrefsService,
    private var mainApiInterface: MainApiInterface
) : BaseViewModel() {

    private val _user = MutableLiveData<Event<UserProfile>>()
    val user: LiveData<Event<UserProfile>> = _user

    fun getUser() {
        request(_user) { mainApiInterface.getUser() }
    }

    fun hasToken() = prefsService.accessToken.isNotEmpty()
}