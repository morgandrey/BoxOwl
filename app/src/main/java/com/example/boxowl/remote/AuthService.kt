package com.example.boxowl.remote

import com.example.boxowl.models.Courier
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

interface AuthService {
    @POST("api/couriers/register")
    fun registerCourier(@Body courier: Courier): Observable<Response<Boolean>>

    @POST("api/couriers/login")
    fun loginCourier(@Body courier: Courier): Observable<Response<Courier>>

    @GET("api/couriers/{id}")
    fun getCourier(@Path("id") courierId: Long): Observable<Courier>
}