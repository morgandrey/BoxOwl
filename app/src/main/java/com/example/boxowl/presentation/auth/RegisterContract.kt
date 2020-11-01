package com.example.boxowl.presentation.auth


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface RegisterContract {
    interface View {
        fun onError(error: String)
        fun onSuccess(user: String)
    }

    interface Presenter {
        fun onSignUpClick(userName: String, userSurname: String, userEmail: String, userPassword: String)
    }
}