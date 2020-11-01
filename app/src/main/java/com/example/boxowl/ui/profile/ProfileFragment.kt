package com.example.boxowl.ui.profile

import com.example.boxowl.bases.FragmentInteractionListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.boxowl.NavigationManager
import com.example.boxowl.R
import com.example.boxowl.bases.BaseFragment
import com.example.boxowl.bases.HasNavigationManager
import com.example.boxowl.databinding.FragmentProfileBinding
import com.example.boxowl.models.CurrentUser
import com.example.boxowl.models.User
import com.example.boxowl.presentation.profile.ProfileContract
import com.example.boxowl.presentation.profile.ProfilePresenter
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.showToast


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class ProfileFragment : BaseFragment(), ProfileContract.View, HasNavigationManager {

    interface OnProfileFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnProfileFragmentInteractionListener
    private lateinit var mNavigationManager: NavigationManager

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profilePresenter: ProfilePresenter

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
        )
        profilePresenter = ProfilePresenter(this)
        loadUserData(CurrentUser.user)
        binding.changeUserProfileBtn.onClick { profilePresenter.updateUserData(changedUser()) }
        return binding.root
    }

    private fun changedUser(): User {
        val user = CurrentUser.user
        user.UserName = binding.firstNameEditText.text.toString()
        user.UserSurname = binding.secondNameEditText.text.toString()
        user.UserEmail = binding.emailEditText.text.toString()
        return user
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProfileFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnProfileFragmentInteractionListener")
        }

        mNavigationManager = NavigationManager(childFragmentManager, R.id.container)
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_profile)
    }

    override fun provideNavigationManager(): NavigationManager = mNavigationManager

    override fun loadUserData(user: User) {
        binding.firstNameEditText.setText(user.UserName)
        binding.secondNameEditText.setText(user.UserSurname)
        binding.emailEditText.setText(user.UserEmail)
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }

    override fun onDestroy() {
        profilePresenter.onDestroy()
        super.onDestroy()
    }
}