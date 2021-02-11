package com.example.boxowl.bases

interface FragmentInteractionListener {
    fun setToolbarTitle(title: String)
    fun setToolbarVisibility(show: Boolean)
    fun setBottomNavigation(show: Boolean, menuId: Int)
    fun setBottomNavigationVisibility(show: Boolean)
}