package com.example.boxowl.remote

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

object Common {
    private const val BASE_URL = "http://10.0.2.2:51104/"
    val authService: AuthService
        get() = RetrofitClient.getClient(BASE_URL).create(AuthService::class.java)
    val profileService: ProfileService
        get() = RetrofitClient.getClient(BASE_URL).create(ProfileService::class.java)
}