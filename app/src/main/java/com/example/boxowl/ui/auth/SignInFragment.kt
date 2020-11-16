package com.example.boxowl.ui.auth

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.databinding.FragmentSignInBinding
import com.example.boxowl.models.Courier
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.presentation.auth.SignInContract
import com.example.boxowl.presentation.auth.SignInPresenter
import com.example.boxowl.ui.extension.hideKeyboard
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.*
import com.google.gson.Gson
import com.wada811.viewbinding.viewBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class SignInFragment : Fragment(R.layout.fragment_sign_in), SignInContract.View {

    private lateinit var signInPresenter: SignInPresenter
    private val binding: FragmentSignInBinding by viewBinding()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInPresenter = SignInPresenter(this)
        sharedPref = requireActivity().getSharedPreferences("COURIER", MODE_PRIVATE)
        signInPresenter.isCourierSignIn(sharedPref)
        loadView()
    }

    private fun loadView() {
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.phoneEditText)
        loadingDialog = loadingSpotsDialog(requireContext())
        with(binding) {
            signUpLabel.onClick { view ->
                view.hideKeyboard()
                view.findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
            }
            signInButton.onClick {
                showLoadingDialog(loadingDialog)
                signInPresenter.onSignInClick(
                    courierPhone = binding.phoneEditText.text.toString().toNormalString(),
                    courierPassword = binding.passwordEditText.text.toString()
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
    }

    override fun onDestroy() {
        signInPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onAPIError(error: String) {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), error)
    }

    override fun onSuccess(courier: Courier) {
        dismissLoadingDialog(loadingDialog)
        val gson = Gson()
        val json = gson.toJson(courier)
        with(sharedPref.edit()) {
            putString("CourierObject", json)
            apply()
        }
        CurrentCourier.courier = courier
        requireView().hideKeyboard()
        requireView().findNavController()
            .navigate(R.id.action_signInFragment_to_pinLockFragment)
    }

    override fun onAuthError() {
        dismissLoadingDialog(loadingDialog)
        showToast(requireContext(), "Неправильный телефон или пароль")
    }
}