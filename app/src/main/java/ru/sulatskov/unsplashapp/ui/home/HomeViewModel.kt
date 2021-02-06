package ru.sulatskov.unsplashapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.BaseViewModel
import ru.sulatskov.unsplashapp.base.viewmodel.Event
import ru.sulatskov.unsplashapp.model.network.MainApiInterface
import ru.sulatskov.unsplashapp.model.network.dto.Photo
import ru.sulatskov.unsplashapp.model.network.dto.UserProfile
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var mainApiInterface: MainApiInterface
) : BaseViewModel() {
    private val _photo = MutableLiveData<Event<List<Photo>>>()
    val photo: LiveData<Event<List<Photo>>> = _photo

    fun getPhotos() {
        request(_photo) { mainApiInterface.getListPhotos(1) }
    }

}