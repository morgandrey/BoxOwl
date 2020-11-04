package com.example.boxowl.presentation.auth

import com.example.boxowl.models.Courier
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

    override fun onSignUpClick(
        courierName: String,
        courierSurname: String,
        courierPhone: String,
        courierPassword: String
    ) {
        authService = Service.authService
        val courier = Courier(
            CourierName = courierName,
            CourierSurname = courierSurname,
            CourierPhone = courierPhone,
            CourierPassword = courierPassword
        )
        compositeDisposable.add(
            authService.registerCourier(courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        if (response.isSuccessful) {
                            view.onSuccess(response.body()!!)
                        }
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