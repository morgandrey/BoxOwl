package com.example.boxowl.presentation.auth

import com.example.boxowl.models.User


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface SignInContract {
    interface View {
        fun onError(error: String)
        fun onSuccess(user: User)
    }

    interface Presenter {
        fun onSignInClick(userEmail: String, userPassword: String)
    }
}