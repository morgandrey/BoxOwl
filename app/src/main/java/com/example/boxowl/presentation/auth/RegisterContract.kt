package com.example.boxowl.presentation.auth


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface RegisterContract {
    interface View {
        fun onError(error: String)
        fun onSuccess(registerStatus: Boolean)
    }

    interface Presenter {
        fun onSignUpClick(
            courierName: String,
            courierSurname: String,
            courierPhone: String,
            courierPassword: String
        )
    }
}