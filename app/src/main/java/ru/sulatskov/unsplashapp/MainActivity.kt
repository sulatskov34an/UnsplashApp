package ru.sulatskov.unsplashapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.ui.auth.OauthFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navView: BottomNavigationView = findViewById(R.id.nav_view)

    navController = findNavController(R.id.nav_host_fragment)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(setOf(
        R.id.navigation_home, R.id.navigation_profile))
    setupActionBarWithNavController(navController, appBarConfiguration)
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
}