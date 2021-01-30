package ru.sulatskov.unsplashapp.ui.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private var prefsService: PrefsService) :BaseViewModel() {

    fun hasToken() = !prefsService.accessToken.isNullOrEmpty()
}