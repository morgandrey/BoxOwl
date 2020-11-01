package com.example.boxowl.presentation.profile

import com.example.boxowl.models.User

/**
 * Created by Andrey Morgunov on 01/11/2020.
 */

interface ProfileContract {
    interface View {
        fun loadUserData(user: User)
        fun onError(error: String)
    }

    interface Presenter {
        fun updateUserData(user: User)
    }
}