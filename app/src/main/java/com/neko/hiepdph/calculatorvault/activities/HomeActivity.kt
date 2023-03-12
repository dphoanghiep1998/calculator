package com.neko.hiepdph.calculatorvault.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initArchitecture()
    }

    private fun initArchitecture() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navController)
        val config = AppBarConfiguration.Builder(navController.graph).setDrawerLayout(
            binding.drawerLayout
        ).build()
        NavigationUI.setupWithNavController(binding.toolbar, navController, config)

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_vault -> navController.navigate(R.id.fragmentVault)
                R.id.item_browser -> navController.navigate(R.id.fragmentBrowser)
                R.id.item_note -> navController.navigate(R.id.fragmentNote)
                R.id.item_recycle_bin -> navController.navigate(R.id.fragmentRecycleBin)
                R.id.item_setting -> navController.navigate(R.id.fragmentSetting)
                R.id.item_language -> navController.navigate(R.id.fragmentLanguage)
                R.id.item_theme -> changeTheme()
                R.id.item_rate_app -> rateApp()
                R.id.item_share_app -> shareApp()
                R.id.item_about_us -> showAboutUs()
                R.id.item_privacy -> showPrivacy()
            }
            true
        }
    }

    private fun showPrivacy() {

    }

    private fun showAboutUs() {

    }

    private fun shareApp() {

    }

    private fun rateApp() {

    }

    private fun changeTheme() {

    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}