package com.example.boxowl.presentation.auth


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface SignInContract {
    interface View {
        fun onError(error: String)
        fun onSuccess(user: String)
    }

    interface Presenter {
        fun onSignInClick(userEmail: String, userPassword: String)
    }
}