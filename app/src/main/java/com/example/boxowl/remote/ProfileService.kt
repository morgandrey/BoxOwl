package com.example.boxowl.remote

import com.example.boxowl.models.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by Andrey Morgunov on 01/11/2020.
 */

interface ProfileService {
    @PUT("api/user/{id}")
    fun updateUserProfile(@Path("id") id: Long, @Body user: User): Observable<Response<User>>
}