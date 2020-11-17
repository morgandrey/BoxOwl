package com.example.boxowl.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.example.boxowl.MainActivity
import com.example.boxowl.R
import com.example.boxowl.databinding.FragmentPinLockBinding
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


/**
 * Created by Andrey Morgunov on 16/11/2020.
 */

class PinLockFragment : Fragment(R.layout.fragment_pin_lock) {

    private val binding: FragmentPinLockBinding by viewBinding()
    private lateinit var sharedPref: SharedPreferences
    private var courierPinCode: String? = ""
    private var pinCodeOne: String = ""
    private var pinCodeTwo: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("COURIER", Context.MODE_PRIVATE)
        courierPinCode = sharedPref.getString("CourierPinCode", null)

        binding.indicatorDots.indicatorType = IndicatorDots.IndicatorType.FIXED
        binding.pinCode.attachIndicatorDots(binding.indicatorDots)

        if (courierPinCode == null) {
            createPinCode()
        } else {
            binding.tvTitle.text =
                "${CurrentCourier.courier.CourierName} ${CurrentCourier.courier.CourierSurname}"
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
            enterPinCode()
        }
    }

    private fun enterPinCode() {
        binding.pinCode.setPinLockListener(pinLockEnterListener)
    }

    private fun createPinCode() {
        binding.pinCode.setPinLockListener(pinLockRegisterListener)
    }

    private val pinLockEnterListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            if (pin == courierPinCode) {
                startActivity(Intent(activity, MainActivity::class.java))
            } else {
                showToast(requireContext(), getString(R.string.wrong_pin_code))
                binding.pinCode.resetPinLockView()
            }
        }

        override fun onEmpty() {}

        override fun onPinChange(pinLength: Int, intermediatePin: String) {}
    }

    private val pinLockRegisterListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            if (pinCodeOne == "") {
                pinCodeOne = pin
                binding.tvTitle.setText(R.string.enter_the_pin_code_again)
                binding.pinCode.resetPinLockView()
            } else {
                pinCodeTwo = pin
                if (pinCodeOne != pinCodeTwo) {
                    showToast(requireContext(), getString(R.string.pin_codes_do_not_match))
                } else {
                    with(sharedPref.edit()) {
                        putString("CourierPinCode", pinCodeOne)
                        apply()
                    }
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }

        }

        override fun onEmpty() {}

        override fun onPinChange(pinLength: Int, intermediatePin: String) {}
    }
}