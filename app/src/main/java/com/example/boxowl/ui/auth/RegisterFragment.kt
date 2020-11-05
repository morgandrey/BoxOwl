package com.example.boxowl.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.databinding.FragmentRegisterBinding
import com.example.boxowl.presentation.auth.RegisterContract
import com.example.boxowl.presentation.auth.RegisterPresenter
import com.example.boxowl.ui.extension.hideKeyboard
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.*
import com.wada811.viewbinding.viewBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class RegisterFragment : Fragment(R.layout.fragment_register), RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter
    private val binding: FragmentRegisterBinding by viewBinding()
    private lateinit var loadingDialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerPresenter = RegisterPresenter(this)
        loadView()
    }

    private fun loadView() {
        loadingDialog = loadingSpotsDialog(requireContext())
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.phoneEditText)

        val registerTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerButton.isEnabled = !binding.userFirstNameEditText.text.isBlank()
                        && !binding.userSecondNameEditText.text.isBlank()
                        && !binding.userPasswordEditText.text.isBlank()
                        && !binding.userConfirmPasswordEditText.text.isBlank()
                        && !binding.phoneEditText.text.toString().isPhone()
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
            userPasswordEditText.addTextChangedListener(registerTextWatcher)
            userConfirmPasswordEditText.addTextChangedListener(registerTextWatcher)

            registerButton.onClick {
                showLoadingDialog(loadingDialog)
                registerPresenter.onSignUpClick(
                    courierName = binding.userFirstNameEditText.text.toString(),
                    courierSurname = binding.userSecondNameEditText.text.toString(),
                    courierPhone = binding.phoneEditText.text.toString().toNormalString(),
                    courierPassword = binding.userPasswordEditText.text.toString()
                )
            }
        }
    }

    override fun onError(error: String) {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), error)
    }

    override fun onSuccess(registerStatus: Boolean) {
        dismissLoadingDialog(loadingDialog)
        when (registerStatus) {
            true -> {
                showToast(requireContext(), "Пользователь зарегистрирован")
                requireView().findNavController()
                    .navigate(R.id.action_registerFragment_to_signInFragment)
            }
            false -> showToast(requireContext(), "Такой пользователь уже существует")
        }
    }

    override fun onDestroy() {
        registerPresenter.onDestroy()
        super.onDestroy()
    }
}