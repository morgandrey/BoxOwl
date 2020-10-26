package com.example.boxowl.models

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

data class User (
    var UserLogin: String = "",
    var UserEmail: String = "",
    var UserPassword: String? = "",
    var UserSalt: String? = ""
)