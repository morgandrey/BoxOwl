package com.example.boxowl.presentation.profile

import com.example.boxowl.models.Courier

/**
 * Created by Andrey Morgunov on 01/11/2020.
 */

interface ProfileContract {
    interface View {
        fun loadUserData(courier: Courier)
        fun onError(error: String)
        fun onSuccess()
    }

    interface Presenter {
        fun updateUserData(user: Courier)
    }
}