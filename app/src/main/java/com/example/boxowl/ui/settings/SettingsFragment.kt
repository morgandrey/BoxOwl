package com.example.boxowl.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.boxowl.MainActivity
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.utils.setAppLocale

class SettingsFragment : PreferenceFragmentCompat() {

    interface OnSettingsFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnSettingsFragmentInteractionListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "language_reply") {
                    val lang = sharedPreferences.getString(key, "")
                    val editor = sharedPreferences.edit()
                    editor.putString(key, lang)
                    editor.apply()
                    setAppLocale(lang!!, requireActivity())
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSettingsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString())
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setToolbarTitle(resources.getString(R.string.title_settings))
        listener.setBottomNavigation(true, R.id.navigation_settings)
    }
}