package com.example.boxowl.presentation.auth

import android.content.SharedPreferences
import com.example.boxowl.models.Courier
import com.example.boxowl.remote.AuthService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class SignInPresenter(private val view: SignInContract.View) : SignInContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var authService: AuthService = Service.authService

    override fun onSignInClick(courierPhone: String, courierPassword: String) {
        val courier = Courier(
            CourierPhone = courierPhone,
            CourierPassword = courierPassword
        )
        compositeDisposable.add(
            authService.loginCourier(courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        when (response.code()) {
                            200 -> view.onSuccess(response.body()!!)
                            204 -> view.onAuthError()
                        }
                    },
                    {
                        view.onAPIError(showAPIErrors(it))
                    })
        )
    }

    override fun isCourierSignIn(sharedPref: SharedPreferences) {
        val gson = Gson()
        val json = sharedPref.getString("CourierObject", null)
        if (json != null) {
            val courier = gson.fromJson(json, Courier::class.java)
            getUser(courier.CourierId)
        }
    }

    private fun getUser(userId: Long) {
        compositeDisposable.add(
            authService.getCourier(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.onSuccess(it)
                    },
                    {
                        view.onAPIError(showAPIErrors(it))
                    })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}