package com.example.boxowl.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.presentation.auth.RegisterPresenter
import com.example.boxowl.databinding.FragmentRegisterBinding
import com.example.boxowl.models.User
import com.example.boxowl.presentation.auth.RegisterContract
import com.example.boxowl.remote.Common
import com.example.boxowl.remote.AuthService
import com.example.boxowl.ui.extension.hideKeyboard
import com.example.boxowl.ui.extension.loadingSpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class RegisterFragment : Fragment(), RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var binding: FragmentRegisterBinding
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

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
        authService = Common.authService

        val registerTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerButton.isEnabled = !binding.userEmailEditText.text.isBlank()
                        && !binding.userLoginEditText.text.isBlank()
                        && !binding.userPasswordEditText.text.isBlank()
                        && !binding.userConfirmPasswordEditText.text.isBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        with(binding) {
            haveAccountLabel.setOnClickListener { view ->
                view.hideKeyboard()
                view.findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
            }
            userLoginEditText.addTextChangedListener(registerTextWatcher)
            userEmailEditText.addTextChangedListener(registerTextWatcher)
            userPasswordEditText.addTextChangedListener(registerTextWatcher)
            userConfirmPasswordEditText.addTextChangedListener(registerTextWatcher)

            registerButton.setOnClickListener { view ->
                if (binding.userPasswordEditText.text.toString() !=
                        binding.userConfirmPasswordEditText.text.toString()) {
                    Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT)
                            .show()
                    return@setOnClickListener
                }
                val alertDialog = loadingSpotsDialog(requireContext())
                alertDialog.show()
                val user = User(
                        UserLogin = binding.userLoginEditText.text.toString(),
                        UserEmail = binding.userEmailEditText.text.toString(),
                        UserPassword = binding.userPasswordEditText.text.toString()
                )
                compositeDisposable.add(
                        authService.registerUser(user)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        {
                                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                                                    .show()
                                            alertDialog.dismiss()
                                            view.findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
                                        }, {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                            .show()
                                    alertDialog.dismiss()
                                })
                )
            }
        }
        return binding.root
    }
}