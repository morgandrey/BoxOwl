package com.example.boxowl.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.*
import com.example.boxowl.databinding.FragmentSignInBinding
import com.example.boxowl.models.CurrentUser
import com.example.boxowl.models.User
import com.example.boxowl.presentation.auth.SignInContract
import com.example.boxowl.presentation.auth.SignInPresenter
import com.example.boxowl.ui.extension.*
import com.example.boxowl.utils.*


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class SignInFragment : Fragment(), SignInContract.View {

    private lateinit var signInPresenter: SignInPresenter
    private lateinit var binding: FragmentSignInBinding
    private lateinit var loadingDialog: AlertDialog

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sign_in,
                container,
                false
        )
        loadingDialog = loadingSpotsDialog(requireContext())
        signInPresenter = SignInPresenter(this)
        with(binding) {
            signUpLabel.onClick { view ->
                view.hideKeyboard()
                view.findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
            }
            signInButton.onClick {
                showLoadingDialog(loadingDialog)
                signInPresenter.onSignInClick(
                        userEmail = binding.emailEditText.text.toString(),
                        userPassword = binding.passwordEditText.text.toString()
                )
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(
                        true
                ) {
                    override fun handleOnBackPressed() {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                })
        return binding.root
    }

    override fun onDestroy() {
        signInPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onError(error: String) {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), error)
    }

    override fun onSuccess(user: User) {
        dismissLoadingDialog(loadingDialog)
        CurrentUser.user = user
        requireView().findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
    }
}