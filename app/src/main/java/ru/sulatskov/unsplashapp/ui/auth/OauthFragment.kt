package ru.sulatskov.unsplashapp.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.base.view.BaseFragment
import ru.sulatskov.unsplashapp.base.viewmodel.Status
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.databinding.FragmentOauthBinding
import ru.sulatskov.unsplashapp.ui.auth.customtabs.CustomTabsHelper

@AndroidEntryPoint
class OauthFragment: BaseFragment() {

    private lateinit var oauthViewModel: OauthViewModel
    private var _binding: FragmentOauthBinding? = null
    private val binding get() = _binding
    private var packageNameToUse: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        oauthViewModel =
            ViewModelProvider(this).get(OauthViewModel::class.java)
        _binding = FragmentOauthBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.login?.setOnClickListener {
            CustomTabsHelper.openCustomTab(view.context, Uri.parse(AppConst.loginUrl))
        }
    }

    fun onNewIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.authority.equals(AppConst.unsplashAuthCallback)) {
                uri.getQueryParameter("code")?.let { code ->
                    oauthViewModel.login(code)
                    oauthViewModel.token.observe(viewLifecycleOwner, Observer {
                        when (it.status) {
                            Status.LOADING -> onProgress()
                            Status.SUCCESS -> onSuccess(it.data)
                            Status.ERROR -> onError()
                        }
                    })
                }
            }
        }
    }

    override fun <T> onSuccess(data: T?) {
        oauthViewModel.saveToken(data as? String)
        findNavController().navigate(R.id.action_to_profile)
    }

    override fun showPlaceholder() {

    }

    override fun hidePlaceholder() {

    }

    override fun setupToolbar() {

    }

    override fun destroyBinding() {
        _binding = null
    }

    override fun onProgress() {

    }

    override fun hideProgress() {

    }
}