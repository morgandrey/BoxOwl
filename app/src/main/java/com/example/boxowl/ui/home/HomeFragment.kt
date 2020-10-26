package com.example.boxowl.ui.home

import com.example.boxowl.bases.FragmentInteractionListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boxowl.R
import com.example.boxowl.bases.BaseFragment


class HomeFragment : BaseFragment() {

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    lateinit var mListener: OnHomeFragmentInteractionListener

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnHomeFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        mListener.setBottomNavigation(true,R.id.navigation_home)
    }
}