package com.example.boxowl.bases

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.boxowl.NavigationManager


open class BaseFragment : Fragment() {

    private lateinit var navigationManagerInner: NavigationManager
    private lateinit var fragmentInteractionInner: FragmentInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationManagerInner = findNavigationManager()
        if (context is Activity)
            fragmentInteractionInner = context as FragmentInteractionListener
        else
            throw RuntimeException("Activity host must implement com.example.boxowl.bases.FragmentInteractionListener")

    }

    fun findNavigationManager(): NavigationManager {
        var parentFrag: Fragment? = this
        while (true) {
            parentFrag = parentFrag?.parentFragment

            if (parentFrag == null)
                break

            if (parentFrag is HasNavigationManager) {
                return (parentFrag as HasNavigationManager).provideNavigationManager()
            }
        }

        if (context is HasNavigationManager)
            return (context as HasNavigationManager).provideNavigationManager()

        throw IllegalArgumentException("No NavigationManager was found")
    }

    fun getNavigationManager(): NavigationManager = navigationManagerInner

    open fun onBackPressed(): Boolean = false

    override fun onStart() {
        super.onStart()
        fragmentInteractionInner.setCurrentFragment(this)
    }
}