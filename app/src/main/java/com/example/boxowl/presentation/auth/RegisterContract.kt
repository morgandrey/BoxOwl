package com.example.boxowl.presentation.auth


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

interface RegisterContract {
    interface View {

    }

    interface Presenter {
        fun onSignUpClick(userEmail: String, userPassword: String)
    }
}