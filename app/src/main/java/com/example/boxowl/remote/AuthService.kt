package com.example.boxowl.remote

import com.example.boxowl.models.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

interface AuthService {
    @POST("api/register")
    fun registerUser(@Body user: User): Observable<String>

    @POST("api/login")
    fun loginUser(@Body user: User): Observable<String>
}