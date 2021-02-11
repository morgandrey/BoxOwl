package com.example.boxowl.presentation.auth

import com.example.boxowl.models.Courier


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface SignInContract {
    interface View {
        fun onAPIError(error: String)
        fun onSuccess(courier: Courier)
        fun onAuthError()
    }

    interface Presenter {
        fun onSignInClick(courierPhone: String, courierPassword: String)
    }
}