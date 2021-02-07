package ru.sulatskov.unsplashapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.ui.auth.OauthFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setStatusBarColor(R.color.color_black)
    val navView: BottomNavigationView = findViewById(R.id.nav_view)
    navView.labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
    navController = findNavController(R.id.nav_host_fragment)
    navView.setupWithNavController(navController)
  }

  override fun onNewIntent(intent: Intent?) {
    val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    val fragment = navFragment?.childFragmentManager?.primaryNavigationFragment
    (fragment as? OauthFragment)?.onNewIntent(intent)
    super.onNewIntent(intent)
  }

  fun hideKeyboard() {
    val imm: InputMethodManager =
      this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
      view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  @SuppressLint("ResourceType")
  private fun setStatusBarColor(color: Int) {
    val window: Window = this.window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
  }
}