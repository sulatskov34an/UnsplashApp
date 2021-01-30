package ru.sulatskov.unsplashapp.ui.auth

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.R
import ru.sulatskov.unsplashapp.base.view.BaseFragment
import ru.sulatskov.unsplashapp.base.viewmodel.Status
import ru.sulatskov.unsplashapp.common.AppConst
import ru.sulatskov.unsplashapp.databinding.FragmentOauthBinding

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
            openCustomTab(view.context, Uri.parse(loginUrl))
        }
    }

    fun onNewIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.authority.equals(unsplashAuthCallback)) {
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

    fun openCustomTab(context: Context, uri: Uri) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .build()
        openCustomTab(context, customTabsIntent, uri)
    }

    private fun openCustomTab(context: Context, customTabsIntent: CustomTabsIntent, uri: Uri) {
        val packageName = getPackageNameToUse(context, uri)

        // If we cant find a package name, it means there's no browser that supports Chrome
        // Custom Tabs installed. So, we fallback to the web-view
        if (packageName == null) {
            launchFallback(context, uri)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                customTabsIntent.intent.putExtra(
                    Intent.EXTRA_REFERRER,
                    Uri.parse("${Intent.URI_ANDROID_APP_SCHEME}//${context.packageName}")
                )
            }
            customTabsIntent.intent.setPackage(packageName)

            try {
                customTabsIntent.launchUrl(context, uri)
            } catch (e: ActivityNotFoundException) {
                launchFallback(context, uri)
            }
        }
    }

    private fun launchFallback(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (context.packageManager.queryIntentActivities(intent, 0).isNotEmpty()) {
            context.startActivity(intent)
        }
    }

    private fun getPackageNameToUse(context: Context, uri: Uri): String? {
        if (packageNameToUse != null) {
            return packageNameToUse
        }
        val pm = context.packageManager

        // Get default VIEW intent handler.
        val activityIntent = Intent(Intent.ACTION_VIEW, uri)
        val defaultHandlerInfo = pm.resolveActivity(activityIntent, 0)
        val defaultHandlerPackageName = defaultHandlerInfo?.activityInfo?.packageName

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs = mutableListOf<String>()
        resolvedActivityList.forEach { info ->
            val serviceIntent = Intent().apply {
                action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
                setPackage(info.activityInfo.packageName)
            }

            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }

        packageNameToUse = when {
            packagesSupportingCustomTabs.isEmpty() -> null
            packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs[0]
            !TextUtils.isEmpty(defaultHandlerPackageName) &&
                    !hasSpecializedHandlerIntents(context, activityIntent)
                    && packagesSupportingCustomTabs.contains(defaultHandlerPackageName)
            -> defaultHandlerPackageName
            packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
            packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
            packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
            packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
            else -> null
        }
        return packageNameToUse
    }

    private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
        try {
            val pm = context.packageManager
            val handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
            if (handlers.size == 0) {
                return false
            }
            handlers.forEach { resolveInfo ->
                val filter = resolveInfo.filter ?: return@forEach
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) return@forEach
                if (resolveInfo.activityInfo == null) return@forEach
                return true
            }
        } catch (e: RuntimeException) {

        }
        return false
    }

    val loginUrl: String
        get() = "https://unsplash.com/oauth/authorize" +
                "?client_id=${AppConst.ACCESS_KEY}" +
                "&redirect_uri=resplash%3A%2F%2F${unsplashAuthCallback}" +
                "&response_type=code" +
                "&scope=public+read_user+write_user+read_photos+write_photos" +
                "+write_likes+write_followers+read_collections+write_collections"

    companion object {
        const val unsplashAuthCallback = "unsplash-auth-callback"
        private const val STABLE_PACKAGE = "com.android.chrome"
        private const val BETA_PACKAGE = "com.chrome.beta"
        private const val DEV_PACKAGE = "com.chrome.dev"
        private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
    }

    override fun <T> onSuccess(data: T?) {
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