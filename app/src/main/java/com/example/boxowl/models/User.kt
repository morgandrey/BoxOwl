package com.example.boxowl.models

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

data class User(
        var UserId: Long = 0,
        var UserName: String = "",
        var UserSurname: String = "",
        var UserPhoto: Int? = null,
        var UserEmail: String = "",
        var UserPassword: String? = "",
        var UserSalt: String? = ""
)