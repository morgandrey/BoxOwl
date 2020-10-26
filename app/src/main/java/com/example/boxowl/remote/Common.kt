package com.example.boxowl.remote

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

object Common {
    private const val BASE_URL = "http://www.footdelivery.somee.com/"
    val authService: AuthService
        get() = RetrofitClient.getClient(BASE_URL).create(AuthService::class.java)
}