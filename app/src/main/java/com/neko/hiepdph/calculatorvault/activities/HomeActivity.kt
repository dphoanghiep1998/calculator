package com.neko.hiepdph.calculatorvault.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu_vault, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        })
    }

    private fun showDialogAddFolder() {

    }

    private fun initArchitecture() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, binding.drawerLayout)
        val config = AppBarConfiguration.Builder(
            setOf(
                R.id.fragmentLanguage,
                R.id.fragmentVault,
                R.id.fragmentSetting,
                R.id.fragmentNote,
                R.id.fragmentBrowser,
                R.id.fragmentRecycleBin
            )
        ).setOpenableLayout(
            binding.drawerLayout
        ).build()
        NavigationUI.setupWithNavController(binding.navigationView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, config)


        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentVault ->{
                    navController.navigate(R.id.fragmentVault)
                }
                R.id.fragmentLanguage ->{
                    navController.navigate(R.id.fragmentLanguage)
                }
                R.id.fragmentBrowser ->{
                    navController.navigate(R.id.fragmentBrowser)
                }
                R.id.fragmentNote ->{
                    navController.navigate(R.id.fragmentNote)
                }
                R.id.fragmentSetting ->{
                    navController.navigate(R.id.fragmentSetting)
                }
                R.id.fragmentRecycleBin ->{
                    navController.navigate(R.id.fragmentRecycleBin)
                }
                R.id.item_theme -> changeTheme()
                R.id.item_rate_app -> rateApp()
                R.id.item_share_app -> shareApp()
                R.id.item_about_us -> showAboutUs()
                R.id.item_privacy -> showPrivacy()
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)
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