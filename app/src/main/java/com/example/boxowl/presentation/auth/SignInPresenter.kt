package com.example.boxowl.presentation.auth

import com.example.boxowl.models.User
import com.example.boxowl.remote.AuthService
import com.example.boxowl.remote.Common
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class SignInPresenter(private val view: SignInContract.View) : SignInContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

    override fun onSignInClick(userEmail: String, userPassword: String) {
        authService = Common.authService
        val user = User(
                UserEmail = userEmail,
                UserPassword = userPassword
        )
        compositeDisposable.add(
                authService.loginUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    view.onSuccess(it.toString())
                                },
                                {
                                    view.onError(it.toString())
                                })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}