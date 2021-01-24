package ru.sulatskov.unsplashapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sulatskov.unsplashapp.ui.profile.ProfileFragment

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
    (fragment as ProfileFragment).onNewIntent(intent)
    super.onNewIntent(intent)
  }
}