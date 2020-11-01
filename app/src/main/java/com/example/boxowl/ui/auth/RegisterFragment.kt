package com.example.boxowl.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.boxowl.*
import com.example.boxowl.presentation.auth.RegisterPresenter
import com.example.boxowl.databinding.FragmentRegisterBinding
import com.example.boxowl.presentation.auth.RegisterContract
import com.example.boxowl.ui.extension.*
import com.example.boxowl.utils.*


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class RegisterFragment : Fragment(), RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var loadingDialog: AlertDialog

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register,
                container,
                false
        )
        registerPresenter = RegisterPresenter(this)
        loadingDialog = loadingSpotsDialog(requireContext())
        val registerTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerButton.isEnabled = !binding.userEmailEditText.text.isBlank()
                        && !binding.userFirstNameEditText.text.isBlank()
                        && !binding.userSecondNameEditText.text.isBlank()
                        && !binding.userPasswordEditText.text.isBlank()
                        && !binding.userConfirmPasswordEditText.text.isBlank()
                        && binding.userEmailEditText.text.toString().isEmail()
                        && binding.userPasswordEditText.text.toString() ==
                        binding.userConfirmPasswordEditText.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        with(binding) {
            haveAccountLabel.onClick { view ->
                view.hideKeyboard()
                view.findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
            }
            userFirstNameEditText.addTextChangedListener(registerTextWatcher)
            userSecondNameEditText.addTextChangedListener(registerTextWatcher)
            userEmailEditText.addTextChangedListener(registerTextWatcher)
            userPasswordEditText.addTextChangedListener(registerTextWatcher)
            userConfirmPasswordEditText.addTextChangedListener(registerTextWatcher)

            registerButton.onClick {
                showLoadingDialog(loadingDialog)
                registerPresenter.onSignUpClick(
                        userName = userFirstNameEditText.text.toString(),
                        userSurname = userSecondNameEditText.text.toString(),
                        userEmail = userEmailEditText.text.toString(),
                        userPassword = userPasswordEditText.text.toString()
                )
            }
        }
        return binding.root
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
        dismissLoadingDialog(loadingDialog)
    }

    override fun onSuccess(user: String) {
        showToast(requireContext(), user)
        dismissLoadingDialog(loadingDialog)
        requireView().findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
    }

    override fun onDestroy() {
        registerPresenter.onDestroy()
        super.onDestroy()
    }
}