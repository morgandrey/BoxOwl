package com.example.boxowl

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.boxowl.bases.BaseFragment
import com.example.boxowl.bases.HasNavigationManager
import com.example.boxowl.databinding.ActivityMainBinding
import com.example.boxowl.ui.home.HomeFragment
import com.example.boxowl.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity(), HasNavigationManager,
    HomeFragment.OnHomeFragmentInteractionListener,
    ProfileFragment.OnProfileFragmentInteractionListener {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var mNavigationManager: NavigationManager
    private lateinit var binding: ActivityMainBinding
    private var mCurrentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mNavigationManager = NavigationManager(supportFragmentManager, R.id.container)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        bottomNavigation = binding.navigation
        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        toolbar = binding.toolbar
        setActionBar(toolbar)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            mNavigationManager.openAsRoot(HomeFragment.newInstance())
            bottomNavigation.menu.getItem(0).isChecked
        }

    }

    override fun provideNavigationManager(): NavigationManager = mNavigationManager

    override fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    override fun setCurrentFragment(fragment: BaseFragment) {
        mCurrentFragment = fragment
    }

    override fun setToolbarVisibility(show: Boolean) {
        toolbar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun setBottomNavigation(show: Boolean, menuId: Int) {
        if (show) {
            bottomNavigation.visibility = View.VISIBLE

            when (menuId) {
                R.id.navigation_home -> bottomNavigation.menu.getItem(0).isChecked = true
                R.id.navigation_profile -> bottomNavigation.menu.getItem(1).isChecked = true
            }

        } else
            bottomNavigation.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (mCurrentFragment == null || !mCurrentFragment!!.onBackPressed())
            super.onBackPressed()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mNavigationManager.open(HomeFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    mNavigationManager.open(ProfileFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
