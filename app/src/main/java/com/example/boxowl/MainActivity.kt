package com.example.boxowl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.boxowl.databinding.ActivityMainBinding
import com.example.boxowl.ui.order.active.HomeFragment
import com.example.boxowl.ui.order.history.OrderHistoryFragment
import com.example.boxowl.ui.profile.ProfileFragment
import com.example.boxowl.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.wada811.viewbinding.viewBinding


class MainActivity : AppCompatActivity(R.layout.activity_main),
    HomeFragment.OnHomeFragmentInteractionListener,
    OrderHistoryFragment.OnHistoryFragmentInteractionListener,
    ProfileFragment.OnProfileFragmentInteractionListener,
    SettingsFragment.OnSettingsFragmentInteractionListener {

    private lateinit var bottomNavigation: BottomNavigationView
    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigation = binding.bottomNavigationView
        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        setActionBar(binding.toolbarView)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            bottomNavigation.menu.getItem(0).isChecked
        }
    }

    override fun setToolbarTitle(title: String) {
        binding.toolbarView.title = title
    }

    override fun setToolbarVisibility(show: Boolean) {
        binding.toolbarView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun setBottomNavigation(show: Boolean, menuId: Int) {
        if (show) {
            bottomNavigation.visibility = View.VISIBLE

            when (menuId) {
                R.id.navigation_home -> bottomNavigation.menu.getItem(0).isChecked = true
                R.id.navigation_order_history -> bottomNavigation.menu.getItem(1).isChecked = true
                R.id.navigation_profile -> bottomNavigation.menu.getItem(2).isChecked = true
                R.id.navigation_settings -> bottomNavigation.menu.getItem(3).isChecked = true
            }

        } else
            bottomNavigation.visibility = View.GONE
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_order_history -> {
                    navController.navigate(R.id.orderHistoryFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    navController.navigate(R.id.profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
