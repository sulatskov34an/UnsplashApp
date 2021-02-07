package ru.sulatskov.unsplashapp.base.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.sulatskov.unsplashapp.MainActivity
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.common.snackbar

abstract class BaseFragment : Fragment(), BaseViewInterface {

    protected open fun <T> onSuccess(data: T?) {}

    protected open fun onError() {
        snackbar(getString(R.string.error_text))
        hideProgress()
        showPlaceholder()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    protected open fun showPlaceholder() {}

    protected open fun hidePlaceholder() {}

    protected open fun setupToolbar() {}

    abstract fun destroyBinding()

    override fun onDestroyView() {
        destroyBinding()
        super.onDestroyView()
    }

    protected open fun onProgress() {}

    protected open fun hideProgress() {}

    fun hideKeyboard() {
        (activity as? MainActivity)?.hideKeyboard()
    }
}