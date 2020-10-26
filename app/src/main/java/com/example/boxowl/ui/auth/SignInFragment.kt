package com.example.boxowl.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.databinding.FragmentSignInBinding
import com.example.boxowl.models.User
import com.example.boxowl.remote.AuthService
import com.example.boxowl.remote.Common
import com.example.boxowl.ui.extension.hideKeyboard
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 22/10/2020.
 */

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

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
        authService = Common.authService
        binding.signUpLabel.setOnClickListener { view ->
            view.hideKeyboard()
            view.findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }
        binding.signInButton.setOnClickListener { view ->
            val alertDialog = SpotsDialog.Builder()
                .setContext(requireContext())
                .build()
            alertDialog.show()
            val user = User(
                UserEmail = binding.emailEditText.text.toString(),
                UserPassword = binding.passwordEditText.text.toString()
            )
            compositeDisposable.add(
                authService.loginUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                                .show()
                            alertDialog.dismiss()
                        }, {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                .show()
                            alertDialog.dismiss()
                        })
            )
            view.findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
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
        compositeDisposable.clear()
        super.onDestroy()
    }
}