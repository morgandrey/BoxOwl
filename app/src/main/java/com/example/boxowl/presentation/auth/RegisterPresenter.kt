package com.example.boxowl.presentation.auth

import com.example.boxowl.models.User
import com.example.boxowl.remote.AuthService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

    override fun onSignUpClick(userName: String, userSurname: String, userEmail: String, userPassword: String) {
        authService = Service.authService
        val user = User(
                UserName = userName,
                UserSurname = userSurname,
                UserEmail = userEmail,
                UserPassword = userPassword
        )
        compositeDisposable.add(
                authService.registerUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    view.onSuccess(it)
                                },
                                {
                                    view.onError(showAPIErrors(it))
                                })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}