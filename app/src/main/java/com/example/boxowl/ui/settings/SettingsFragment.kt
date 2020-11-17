package com.example.boxowl.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentSettingsBinding
import com.wada811.viewbinding.viewBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    interface OnSettingsFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnSettingsFragmentInteractionListener
    private val binding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSettingsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnSettingsFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_settings)
        listener.setToolbarTitle(resources.getString(R.string.title_settings))
    }
}