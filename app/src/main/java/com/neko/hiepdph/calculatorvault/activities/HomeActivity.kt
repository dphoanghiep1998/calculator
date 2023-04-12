package com.neko.hiepdph.calculatorvault.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.databinding.ActivityHomeBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.note.ToolbarChangeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ToolbarChangeListener {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var config: AppBarConfiguration? = null
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initArchitecture()
    }

    fun getToolbar(): Toolbar {
        return binding.toolbar
    }

    private fun initArchitecture() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController!!, binding.drawerLayout)
        config = AppBarConfiguration(
            setOf(
                R.id.fragmentLanguage,
                R.id.fragmentVault,
                R.id.fragmentSetting,
                R.id.fragmentNote,
                R.id.fragmentBrowser,
                R.id.fragmentRecycleBin
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController!!)
        NavigationUI.setupWithNavController(binding.navigationView, navController!!)
        NavigationUI.setupWithNavController(binding.toolbar, navController!!, config!!)
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentVault -> {
//                    navController!!.clearBackStack(R.id.fragmentVault)
                    navController!!.navigate(R.id.fragmentVault)
                }
                R.id.fragmentLanguage -> {
//                    navController!!.clearBackStack(R.id.fragmentLanguage)
                    navController!!.navigate(R.id.fragmentLanguage)
                }
                R.id.fragmentBrowser -> {
                    navController!!.clearBackStack(R.id.fragmentBrowser)
                    navController!!.navigate(R.id.fragmentBrowser)

                }
                R.id.fragmentNote -> {
//                    navController!!.clearBackStack(R.id.fragmentNote)
                    navController!!.navigate(R.id.fragmentNote)
                }
                R.id.fragmentSetting -> {
//                    navController!!.clearBackStack(R.id.fragmentSetting)
                    navController!!.navigate(R.id.fragmentSetting)
                }
                R.id.fragmentRecycleBin -> {
//                    navController!!.clearBackStack(R.id.fragmentRecycleBin)
                    navController!!.navigate(R.id.fragmentRecycleBin)
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

    override fun setToolBarNavigationIcon(drawable: Drawable) {
        binding.toolbar.navigationIcon = drawable
    }

    override fun setOnClickToNavigationIcon(callback: () -> Unit) {
        binding.toolbar.setNavigationOnClickListener {
            callback.invoke()
            NavigationUI.setupWithNavController(binding.toolbar, navController!!, config!!)
        }
    }


}