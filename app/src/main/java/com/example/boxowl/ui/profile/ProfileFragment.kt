package com.example.boxowl.ui.profile

import com.example.boxowl.bases.FragmentInteractionListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boxowl.NavigationManager
import com.example.boxowl.R
import com.example.boxowl.bases.BaseFragment
import com.example.boxowl.bases.HasNavigationManager

class ProfileFragment : BaseFragment(), HasNavigationManager {

    private lateinit var mListener: OnProfileFragmentInteractionListener
    private lateinit var mNavigationManager: NavigationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProfileFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnProfileFragmentInteractionListener")
        }

        mNavigationManager= NavigationManager(childFragmentManager,R.id.container)
    }

    interface OnProfileFragmentInteractionListener : FragmentInteractionListener

    override fun onStart() {
        super.onStart()

        mListener.setBottomNavigation(true, R.id.navigation_profile)
    }

    override fun provideNavigationManager(): NavigationManager =mNavigationManager


    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}