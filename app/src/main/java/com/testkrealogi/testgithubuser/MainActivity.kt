package com.testkrealogi.testgithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.testkrealogi.testgithubuser.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    val navController = navHostFragment.navController
    navController.addOnDestinationChangedListener { _, destination, _ ->
      if (destination.id == R.id.mainFragment) {
        supportActionBar?.hide()
        actionBar?.hide()
      } else {
        supportActionBar?.show()
        actionBar?.show()
      }
    }

    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}